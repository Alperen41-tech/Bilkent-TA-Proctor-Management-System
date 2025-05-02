package com.cs319group3.backend.Services.ServiceImpls.RequestServiceImpls;

import com.cs319group3.backend.DTOMappers.RequestMappers.RequestMapper;
import com.cs319group3.backend.DTOs.ProctoringApplicationDTO;
import com.cs319group3.backend.DTOs.RequestDTOs.RequestDTO;
import com.cs319group3.backend.Entities.Notification;
import com.cs319group3.backend.Entities.RequestEntities.*;
import com.cs319group3.backend.Entities.UserEntities.User;
import com.cs319group3.backend.Repositories.*;
import com.cs319group3.backend.Services.NotificationService;
import com.cs319group3.backend.Services.RequestService;
import com.cs319group3.backend.Services.TASwapRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.cs319group3.backend.Enums.NotificationType.APPROVAL;

@Service
public class RequestServiceImpl implements RequestService {

    @Autowired
    private RequestRepo requestRepo;

    @Autowired
    private NotificationRepo notificationRepo;

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


    @Override
    public boolean respondToRequest(int requestId, boolean response) {
        Optional<Request> optionalRequest = requestRepo.findByRequestId(requestId);
        if (optionalRequest.isEmpty()) {
            return false;
        }

        Request request = optionalRequest.get();

        try{
            if (request instanceof TAWorkloadRequest) {
                TAWorkloadRequest req = (TAWorkloadRequest) request;

            } else if (request instanceof TASwapRequest) {
                if (response){
                    TASwapRequest req = (TASwapRequest) request;
                    swapRequestService.acceptSwapRequest(requestId);
                }
            } else if (request instanceof TALeaveRequest) {
                TALeaveRequest req = (TALeaveRequest) request;

            } else if (request instanceof InstructorAdditionalTARequest) {
                InstructorAdditionalTARequest req = (InstructorAdditionalTARequest) request;

            } else if (request instanceof AuthStaffProctoringRequest) {
                AuthStaffProctoringRequest req = (AuthStaffProctoringRequest) request;

            } else {
                return false; // unknown type
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }

        request.setApproved(response);
        request.setResponseDate(LocalDateTime.now());
        requestRepo.save(request);

        notificationService.createNotification(request, APPROVAL);

        return true;
    }

    @Override
    public boolean deleteRequest(int requestId) {

        Optional<Request> optionalRequest = requestRepo.findByRequestId(requestId);
        if (optionalRequest.isPresent()) {
            Request request = optionalRequest.get();
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
        List<TAWorkloadRequest> workloadRequests = taWorkloadRequestRepo.findBySenderUser_UserId(userId);
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

    @Autowired
    DepartmentSecretaryRepo departmentSecretaryRepo;

    @Autowired
    UserRepo userRepo;

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
