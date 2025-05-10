package com.cs319group3.backend.Services.ServiceImpls.RequestServiceImpls;

import com.cs319group3.backend.DTOMappers.RequestMappers.RequestMapper;
import com.cs319group3.backend.DTOs.ProctoringApplicationDTO;
import com.cs319group3.backend.DTOs.RequestDTOs.RequestDTO;
import com.cs319group3.backend.Entities.Notification;
import com.cs319group3.backend.Entities.RequestEntities.*;
import com.cs319group3.backend.Entities.UserEntities.TA;
import com.cs319group3.backend.Entities.UserEntities.User;
import com.cs319group3.backend.Enums.LogType;
import com.cs319group3.backend.Repositories.*;
import com.cs319group3.backend.Services.*;
import com.cs319group3.backend.Services.ServiceImpls.ClassProctoringTARelationServiceImpl;
import com.cs319group3.backend.Services.ServiceImpls.LogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static com.cs319group3.backend.Enums.NotificationType.APPROVAL;

@Service
public class RequestServiceImpl implements RequestService {

    @Autowired
    private TARepo taRepo;

    @Autowired
    private RequestRepo requestRepo;

    @Autowired
    private TASwapRequestRepo taswapRequestRepo;

    @Autowired
    private TAWorkloadRequestRepo taWorkloadRequestRepo;

    @Autowired
    private TALeaveRequestRepo taleaveRequestRepo;

    @Autowired
    private InstructorAdditionalTARequestRepo instructorAdditionalTARequestRepo;

    @Autowired
    private AuthStaffProctoringRequestRepo authStaffProctoringRequestRepo;

    @Autowired
    private TASwapRequestService swapRequestService;

    @Autowired
    private RequestMapper requestMapper;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private AuthStaffProctoringRequestService authStaffProctoringRequestService;

    @Autowired
    private LogService logService;

    @Autowired
    private ClassProctoringTARelationService classProctoringTARelationService;

    @Autowired
    DepartmentSecretaryRepo departmentSecretaryRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    TAAvailabilityService taAvailabilityService;

    @Autowired
    ClassProctoringRepo classProctoringRepo;

    @Override
    public boolean respondToRequest(int requestId, boolean response) throws Exception {
        Optional<Request> optionalRequest = requestRepo.findByRequestId(requestId);
        if (optionalRequest.isEmpty()) {
            System.out.println("Request cannot be found");
            return false;
        }

        Request request = optionalRequest.get();

        if (response){
            if (request instanceof TAWorkloadRequest) {
                Optional<TA> ta = taRepo.findById(request.getSenderUser().getUserId());
                if (ta.isEmpty()) {
                    throw new RuntimeException("No such TA");
                }
                ta.get().setWorkload(ta.get().getWorkload() + ((TAWorkloadRequest) request).getTimeSpent());
                taRepo.save(ta.get());
            }
            else if (request instanceof TASwapRequest) {
                TASwapRequest req = (TASwapRequest) request;
                swapRequestService.acceptSwapRequest(requestId);
            }
            else if (request instanceof AuthStaffProctoringRequest) {
                AuthStaffProctoringRequest req = (AuthStaffProctoringRequest) request;
                if (!authStaffProctoringRequestService.canRequestBeAccepted(req.getClassProctoring().getClassProctoringId())) {
                    request.setApproved(false);
                    request.setResponseDate(LocalDateTime.now());
                    requestRepo.save(request);
                    System.out.println("Request is automatically rejected due to full proctoring");
                    return false;
                } else if (!taAvailabilityService.isTAAvailable((TA) req.getReceiverUser(), req.getClassProctoring())) {
                    request.setApproved(false);
                    request.setResponseDate(LocalDateTime.now());
                    requestRepo.save(request);
                    System.out.println("Request is automatically rejected due to unavailability of the user");
                    return false;
                } else {
                    classProctoringTARelationService.createClassProctoringTARelation(req.getReceiverUser().getUserId(), req.getClassProctoring().getClassProctoringId());
                    Optional<TA> ta = taRepo.findById(request.getReceiverUser().getUserId());
                    if (ta.isEmpty()) {
                        throw new RuntimeException("No such TA");
                    }
                    long minutes = ChronoUnit.MINUTES.between(req.getClassProctoring().getStartDate(), req.getClassProctoring().getEndDate());
                    ta.get().setWorkload(ta.get().getWorkload() + (int) minutes);
                    authStaffProctoringRequestService.rejectRequestsIfNeeded(req.getClassProctoring().getClassProctoringId());
                }
            }
            else if (request instanceof TASwapRequest) {
                TASwapRequest req = (TASwapRequest) request;
                swapRequestService.acceptSwapRequest(requestId);
            }
            else if (request instanceof AuthStaffProctoringRequest) {
                AuthStaffProctoringRequest req = (AuthStaffProctoringRequest) request;
                if(!authStaffProctoringRequestService.canRequestBeAccepted(req.getClassProctoring().getClassProctoringId())) {
                    request.setApproved(false);
                    request.setResponseDate(LocalDateTime.now());
                    requestRepo.save(request);
                    System.out.println("Request is automatically rejected due to full proctoring");
                    return false;
                }
                else if(!taAvailabilityService.isTAAvailable((TA) req.getReceiverUser(), req.getClassProctoring())){
                    request.setApproved(false);
                    request.setResponseDate(LocalDateTime.now());
                    requestRepo.save(request);
                    System.out.println("Request is automatically rejected due to unavailability of the user");
                    return false;
                }
                else{
                    classProctoringTARelationService.createClassProctoringTARelation(req.getReceiverUser().getUserId(), req.getClassProctoring().getClassProctoringId());
                    Optional<TA> ta = taRepo.findById(request.getReceiverUser().getUserId());
                    if (ta.isEmpty()) {
                        throw new RuntimeException("No such TA");
                    }
                    long minutes = ChronoUnit.MINUTES.between(req.getClassProctoring().getStartDate(), req.getClassProctoring().getEndDate());
                    ta.get().setWorkload(ta.get().getWorkload() + (int)minutes);
                    authStaffProctoringRequestService.rejectRequestsIfNeeded(req.getClassProctoring().getClassProctoringId());
                }
            }
            else {
                return false; // unknown type
            }
        }

        if (request instanceof TAWorkloadRequest) {
            TAWorkloadRequest req = (TAWorkloadRequest) request;
            List<TAWorkloadRequest> reqs = taWorkloadRequestRepo.findByWorkloadIdAndRequestIdNot(req.getWorkloadId(), requestId);
            for (TAWorkloadRequest req1 : reqs) {
                if (req1.getRequestId() != requestId) {
                    requestRepo.delete(req1);
                }
            }
        }
        request.setApproved(response);
        request.setResponseDate(LocalDateTime.now());
        String logMessage;
        if (response)
            logMessage = "Request " + request.getRequestId() + " accepted by user " + request.getReceiverUser().getUserId() + ".";
        else
            logMessage = "Request " + request.getRequestId() + " rejected by user " + request.getReceiverUser().getUserId() + ".";
        logService.createLog(logMessage, LogType.UPDATE);
        try {
            requestRepo.save(request);
        } catch (Exception e) {
            e.printStackTrace();  // or log the error
            throw e;
        }

        notificationService.createNotification(request, APPROVAL);
        return true;
    }

    @Override
    public boolean deleteRequest(int requestId) {

        Optional<Request> optionalRequest = requestRepo.findByRequestId(requestId);
        if (optionalRequest.isPresent()) {
            Request request = optionalRequest.get();
            if (request instanceof TAWorkloadRequest) {
                int workloadId = ((TAWorkloadRequest) request).getWorkloadId();
                List<TAWorkloadRequest> reqs = taWorkloadRequestRepo.findByWorkloadId(workloadId);
                for (TAWorkloadRequest req : reqs) {
                    requestRepo.delete(req);
                }
                return true;
            }
            requestRepo.delete(request);
            return true;
        }

        return false;
    }

    @Override
    public List<RequestDTO> getRequestsByReceiverUser(int userId) {
        List<RequestDTO> requests = new ArrayList<>();


        List<TASwapRequest> taSwapRequests = taswapRequestRepo.findByReceiverUser_UserId(userId);
        List<TAWorkloadRequest> workloadRequests = taWorkloadRequestRepo.findByReceiverUser_UserId(userId);
        List<TALeaveRequest> taleaveRequests = taleaveRequestRepo.findByReceiverUser_UserId(userId);
        List<InstructorAdditionalTARequest> instructorAdditionalTARequests = instructorAdditionalTARequestRepo.findByReceiverUser_UserId(userId);
        List<AuthStaffProctoringRequest>  authStaffProctoringRequests = authStaffProctoringRequestRepo.findByReceiverUser_UserId(userId);

        requests.addAll(requestMapper.taSwapRequestMapper(taSwapRequests));
        requests.addAll(requestMapper.taWorkloadRequestMapper(workloadRequests));
        requests.addAll(requestMapper.taLeaveRequestMapper(taleaveRequests));
        requests.addAll(requestMapper.instructorAdditionalTARequestMapper(instructorAdditionalTARequests));
        requests.addAll(requestMapper.authStaffTARequestMapper(authStaffProctoringRequests));

        return requests;
    }

    @Override
    public List<RequestDTO> getRequestsBySenderUser(int userId) {
        List<RequestDTO> requests = new ArrayList<>();


        List<TASwapRequest> taSwapRequests = taswapRequestRepo.findBySenderUser_UserId(userId);
        List<TAWorkloadRequest> preWorkloadRequests = taWorkloadRequestRepo.findBySenderUser_UserId(userId);
        List<TAWorkloadRequest> workloadRequests = new ArrayList<>();
        Set<Integer> workloadIdSet = new HashSet<>();
        for (TAWorkloadRequest taWorkloadRequest : preWorkloadRequests) {
            if (!workloadIdSet.contains(taWorkloadRequest.getWorkloadId())) {
                workloadIdSet.add(taWorkloadRequest.getWorkloadId());
                workloadRequests.add(taWorkloadRequest);
            }
        }
        List<TALeaveRequest> taleaveRequests = taleaveRequestRepo.findBySenderUser_UserId(userId);
        List<InstructorAdditionalTARequest> instructorAdditionalTARequests = instructorAdditionalTARequestRepo.findBySenderUser_UserId(userId);
        List<AuthStaffProctoringRequest>  authStaffProctoringRequests = authStaffProctoringRequestRepo.findBySenderUser_UserId(userId);

        requests.addAll(requestMapper.taSwapRequestMapper(taSwapRequests));
        requests.addAll(requestMapper.taWorkloadRequestMapper(workloadRequests));
        requests.addAll(requestMapper.taLeaveRequestMapper(taleaveRequests));
        requests.addAll(requestMapper.instructorAdditionalTARequestMapper(instructorAdditionalTARequests));
        requests.addAll(requestMapper.authStaffTARequestMapper(authStaffProctoringRequests));
        return requests;
    }

    @Override
    public Request createProctoringApplicationRequest(ProctoringApplicationDTO paDTO, int deansOfficeId){
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setIsApproved(true);

        int departmentId = paDTO.getVisibleDepartmentId();
        requestDTO.setSentDateTime(LocalDateTime.now());
        requestDTO.setResponseDateTime(LocalDateTime.now());
        Request request = RequestMapper.toEntity(requestDTO);
        request.setReceiverUser(departmentSecretaryRepo.findByDepartmentDepartmentId(paDTO.getVisibleDepartmentId()).get());
        request.setSenderUser(userRepo.findByUserId(deansOfficeId).get());
        requestRepo.save(request);
        return request;
    }
}
