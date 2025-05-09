package com.cs319group3.backend.Services.ServiceImpls.RequestServiceImpls;

import com.cs319group3.backend.DTOs.TAProfileDTO;
import com.cs319group3.backend.Entities.ClassProctoring;
import com.cs319group3.backend.Entities.RelationEntities.ClassProctoringTARelation;
import com.cs319group3.backend.Entities.RelationEntities.ProctoringApplicationTARelation;
import com.cs319group3.backend.Entities.RequestEntities.AuthStaffProctoringRequest;
import com.cs319group3.backend.Entities.UserEntities.TA;
import com.cs319group3.backend.Entities.UserEntities.User;
import com.cs319group3.backend.Enums.NotificationType;
import com.cs319group3.backend.Repositories.*;
import com.cs319group3.backend.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class AuthStaffProctoringRequestServiceImpl implements AuthStaffProctoringRequestService {

    @Autowired
    private AuthStaffProctoringRequestRepo authStaffProctoringRequestRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ClassProctoringRepo classProctoringRepo;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private ClassProctoringTARelationRepo classProctoringTARelationRepo;

    @Autowired
    private TARepo taRepo;

    @Autowired
    private ClassProctoringRepo classProcRepo;

    @Autowired
    private TAAvailabilityService taAvailabilityService;

    @Autowired
    private ClassProctoringTARelationService classProctoringTARelationService;

    @Autowired
    private ProctoringApplicationTARelationService proctoringApplicationTARelationService;

    @Autowired
    private ClassProctoringService classProctoringService;

    @Autowired
    @Lazy
    private TAService taService;

    @Override
    public boolean isRequestAlreadySent(int senderId, int receiverId, int classProctoringId) {
        List<AuthStaffProctoringRequest> request =
                authStaffProctoringRequestRepo.findBySenderUserUserIdAndReceiverUserUserIdAndClassProctoringClassProctoringIdAndApprovedFalseAndResponseDateIsNull(
                        senderId, receiverId, classProctoringId);
        return !request.isEmpty();
    }

    @Override
    public boolean sendAuthStaffProctoringRequest(int classProctoringId, int taId, int senderId, boolean isApproved) {
        if (isApproved && !canForcedRequestBeSent(classProctoringId)) {
            System.out.println("Proctoring is full");
            return false;
        }

        if (!isApproved && !canUnforcedRequestBeSent(classProctoringId)) {
            System.out.println("Too many requests");
            return false;
        }

        Optional<ClassProctoringTARelation> cptr =
                classProctoringTARelationRepo.findById_ClassProctoringIdAndId_TAId(classProctoringId, taId);
        Optional<TA> taOpt = taRepo.findByUserId(taId);
        Optional<ClassProctoring> cpOptional = classProcRepo.findById(classProctoringId);

        if (taOpt.isEmpty()) {
            System.out.println("TA not found");
            return false;
        }
        if (cpOptional.isEmpty()) {
            System.out.println("ClassProctoring not found");
            return false;
        }
        if (cptr.isPresent()) {
            System.out.println("Already assigned");
            return false;
        }
        if (!taAvailabilityService.isTAAvailable(taOpt.get(), cpOptional.get())) {
            System.out.println("TA not available");
            return false;
        }
        if (isRequestAlreadySent(senderId, taId, classProctoringId)) {
            System.out.println("An unapproved request is already sent");
            return false;
        }

        AuthStaffProctoringRequest request = new AuthStaffProctoringRequest();

        Optional<User> sender = userRepo.findById(senderId);
        if (sender.isPresent()) {
            request.setSenderUser(sender.get());
        } else {
            System.out.println("Given sender is not found");
            return false;
        }

        Optional<User> receiver = userRepo.findById(taId);
        if (receiver.isPresent()) {
            request.setReceiverUser(receiver.get());
        } else {
            System.out.println("Given receiver is not found");
            return false;
        }

        Optional<ClassProctoring> cpOpt = classProctoringRepo.findById(classProctoringId);
        if (cpOpt.isPresent()) {
            ClassProctoring cp = cpOpt.get();
            request.setClassProctoring(cp);
            request.setApproved(isApproved);
            request.setSentDate(LocalDateTime.now());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            if (!isApproved) {
                String unapprovedDescription = "You are requested for proctoring duty of the event \"" +
                        cp.getEventName() + "\" starts at " + cp.getStartDate().format(formatter) +
                        " and ends at " + cp.getEndDate().format(formatter) + ".";
                request.setDescription(unapprovedDescription);
                authStaffProctoringRequestRepo.save(request);
                notificationService.createNotification(request, NotificationType.REQUEST, unapprovedDescription);
                System.out.println(unapprovedDescription);
            } else {
                authStaffProctoringRequestRepo.save(request);
                String approvedDescription = "You are assigned for proctoring duty of the event " +
                        cp.getEventName() + " starts at " + cp.getStartDate().format(formatter) +
                        " and ends at " + cp.getEndDate().format(formatter) + ".";
                notificationService.createNotification(request, NotificationType.ASSIGNMENT, approvedDescription);
                System.out.println(approvedDescription);
                classProctoringTARelationService.createClassProctoringTARelation(taId, classProctoringId);
            }
        } else {
            System.out.println("Given proctoring is not found");
            return false;
        }

        return true;
    }

    @Override
    public boolean sendAuthStaffProctoringRequests(List<TAProfileDTO> dtoList, int classProctoringId, int senderId, boolean isApproved) {
        for (TAProfileDTO dto : dtoList) {
            boolean sent = sendAuthStaffProctoringRequest(classProctoringId, dto.getUserId(), senderId, isApproved);
            proctoringApplicationTARelationService.deleteProctoringApplicationTARelation(classProctoringId, dto);
            System.out.println("Request is sent? " + sent);
        }
        return true;
    }

    @Override
    public List<TAProfileDTO> sendAuthStaffProctoringRequestAutomaticallyInDepartment(int classProctoringId, String departmentCode, int senderId, int count, boolean eligibilityRestriction, boolean oneDayRestriction) {
        List<TAProfileDTO> availableTAs =
                taService.getAllAvailableTAsByDepartmentCode(departmentCode, classProctoringId, senderId, eligibilityRestriction, oneDayRestriction);
        if (availableTAs.size() < count) {
            throw new RuntimeException("There are not enough available tas");
        }
        availableTAs.sort(
                Comparator.comparing(TAProfileDTO::isTAOfTheCourse, Comparator.reverseOrder())
                        .thenComparing(TAProfileDTO::getWorkload)
        );
        return availableTAs.subList(0, count);
    }

    @Override
    public List<TAProfileDTO> sendAuthStaffProctoringRequestAutomaticallyInFaculty(int classProctoringId, int facultyId, int senderId, int count, boolean eligibilityRestriction, boolean oneDayRestriction) {
        List<TAProfileDTO> availableTAs =
                taService.getAllAvailableTAsByFacultyId(facultyId, classProctoringId, senderId, eligibilityRestriction, oneDayRestriction);
        if (availableTAs.size() < count) {
            throw new RuntimeException("There are not enough available tas");
        }
        availableTAs.sort(
                Comparator.comparing(TAProfileDTO::isTAOfTheCourse, Comparator.reverseOrder())
                        .thenComparing(TAProfileDTO::getWorkload)
        );
        return availableTAs.subList(0, count);
    }

    @Override
    public boolean canUnforcedRequestBeSent(int classProctoringId) {
        int tasAssigned = classProctoringService.numberOfTAsAssigned(classProctoringId);
        int totalUnapprovedRequests = classProctoringService.numberOfRequestsSent(classProctoringId);
        int taCount = classProctoringRepo.findCountByClassProctoringId(classProctoringId);
        if (tasAssigned >= taCount) {
            System.out.println("There is no place left in classProctoring: " + classProctoringId);
            return false;
        }
        return tasAssigned + totalUnapprovedRequests < taCount + 3;
    }

    @Override
    public boolean canForcedRequestBeSent(int classProctoringId) {
        int tasAssigned = classProctoringService.numberOfTAsAssigned(classProctoringId);
        int taCount = classProctoringRepo.findCountByClassProctoringId(classProctoringId);
        return tasAssigned < taCount;
    }

    @Override
    public boolean canRequestBeAccepted(int classProctoringId) {
        int tasAssigned = classProctoringService.numberOfTAsAssigned(classProctoringId);
        int taCount = classProctoringRepo.findCountByClassProctoringId(classProctoringId);
        return tasAssigned < taCount;
    }

    @Override
    public void rejectRequestsIfNeeded(int classProctoringId) {
        int taCount = classProctoringRepo.findCountByClassProctoringId(classProctoringId);
        int assignedTAs = classProctoringService.numberOfTAsAssigned(classProctoringId);

        if (assignedTAs >= taCount) {
            List<AuthStaffProctoringRequest> unresponded =
                    authStaffProctoringRequestRepo.findByClassProctoringClassProctoringIdAndResponseDateIsNullAndApprovedFalse(classProctoringId);

            for (AuthStaffProctoringRequest request : unresponded) {
                request.setResponseDate(LocalDateTime.now());
                String description = " The request sent regarding to the class proctoring " +
                        request.getClassProctoring().getEventName() +
                        " is automatically rejected because capacity has been filled. ";
                System.out.println(description);
                notificationService.createNotification(request, NotificationType.APPROVAL, description);
            }
        }
    }
}