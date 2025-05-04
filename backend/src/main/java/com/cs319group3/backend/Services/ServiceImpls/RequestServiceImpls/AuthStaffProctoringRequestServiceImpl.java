package com.cs319group3.backend.Services.ServiceImpls.RequestServiceImpls;

import com.cs319group3.backend.Entities.ClassProctoring;
import com.cs319group3.backend.Entities.RelationEntities.ClassProctoringTARelation;
import com.cs319group3.backend.Entities.RequestEntities.AuthStaffProctoringRequest;
import com.cs319group3.backend.Entities.UserEntities.User;
import com.cs319group3.backend.Enums.NotificationType;
import com.cs319group3.backend.Repositories.*;
import com.cs319group3.backend.Services.*;
import com.cs319group3.backend.Services.ServiceImpls.UserServiceImpls.TAServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class AuthStaffProctoringRequestServiceImpl implements AuthStaffProctoringRequestService {

    @Autowired
    AuthStaffProctoringRequestRepo authStaffProctoringRequestRepo;

    @Override
    public boolean isRequestAlreadySent(int senderId, int receiverId, int classProctoringId){
        Optional<AuthStaffProctoringRequest> request = authStaffProctoringRequestRepo.findBySenderUserUserIdAndReceiverUserUserIdAndClassProctoringClassProctoringId(senderId, receiverId, classProctoringId);
        return request.isPresent();
    }

    @Autowired
    UserRepo userRepo;

    @Autowired
    ClassProctoringRepo classProctoringRepo;

    @Autowired
    NotificationService notificationService;

    @Autowired
    ClassProctoringTARelationRepo classProctoringTARelationRepo;

    @Autowired
    TARepo taRepo;

    @Autowired
    ClassProctoringRepo classProcRepo;

    @Autowired
    TAAvailabilityService taAvailabilityService;

    @Override
    public boolean sendAuthStaffProctoringRequest(int classProctoringId, int taId, int senderId, boolean isApproved) {

        if(isApproved && !canForcedRequestBeSent(classProctoringId)) {
            return false;
        }

        if(!isApproved && !canUnforcedRequestBeSent(classProctoringId)) {
            return false;
        }

        Optional<ClassProctoringTARelation> cptr = classProctoringTARelationRepo.findById_ClassProctoringIdAndId_TAId(taId, classProctoringId);
        if(cptr.isPresent() || !taAvailabilityService.isTAAvailable(taRepo.findByUserId(taId).get(), classProcRepo.findById(classProctoringId).get())) {
            return false;
        }

        if(isRequestAlreadySent(senderId, taId, classProctoringId) ) {
            return false;
        }
        AuthStaffProctoringRequest request = new AuthStaffProctoringRequest();

        Optional<User> sender = userRepo.findById(senderId);
        if (sender.isPresent()) {
            request.setSenderUser(sender.get());
        } else {
            return false;
        }

        Optional<User> receiver = userRepo.findById(taId);
        if (receiver.isPresent()) {
            request.setReceiverUser(receiver.get());
        } else {
            return false;
        }

        Optional<ClassProctoring> cpOpt = classProctoringRepo.findById(classProctoringId);
        if (cpOpt.isPresent()) {
            ClassProctoring cp = cpOpt.get();
            request.setClassProctoring(cp);
            request.setApproved(isApproved);
            request.setSentDate(LocalDateTime.now());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            if(!isApproved) {
                String unapprovedDescription = "You are requested for proctoring duty of the event \"" +
                        cp.getEventName() + "\" starts at " + cp.getStartDate().format(formatter) +
                        " and ends at " + cp.getEndDate().format(formatter) + ".";
                request.setDescription(unapprovedDescription);
                authStaffProctoringRequestRepo.save(request);
                notificationService.createNotification(request, NotificationType.REQUEST, unapprovedDescription);
                System.out.println(unapprovedDescription);
            }
            else{
                authStaffProctoringRequestRepo.save(request);
                String approvedDescription = "You are assigned for proctoring duty of the event " +
                        cp.getEventName() + " starts at " + cp.getStartDate().format(formatter) +
                        " and ends at " + cp.getEndDate().format(formatter) + ".";
                notificationService.createNotification(request, NotificationType.ASSIGNMENT, approvedDescription);
                System.out.println(approvedDescription);
            }
        }
        else {
            return false;
        }
        return true;
    }

    @Autowired
    ClassProctoringService classProctoringService;

    @Override
    public boolean canUnforcedRequestBeSent(int classProctoringId) {
        int tasAssigned = classProctoringService.numberOfTAsAssigned(classProctoringId);
        int totalUnapprovedRequests = classProctoringService.numberOfRequestsSent(classProctoringId);
        int taCount = classProctoringRepo.findCountByClassProctoringId(classProctoringId);
        if(tasAssigned >= taCount){
            return false;
        }
        return tasAssigned + totalUnapprovedRequests < taCount + 3;
    }

    @Override
    public boolean canForcedRequestBeSent(int classProctoringId){
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
}
