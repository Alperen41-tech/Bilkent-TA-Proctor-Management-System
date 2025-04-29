package com.cs319group3.backend.Services.ServiceImpls.RequestServiceImpls;

import com.cs319group3.backend.DTOMappers.RequestMappers.RequestMapper;
import com.cs319group3.backend.DTOs.RequestDTOs.RequestDTO;
import com.cs319group3.backend.Entities.Notification;
import com.cs319group3.backend.Entities.RequestEntities.Request;
import com.cs319group3.backend.Entities.RequestEntities.TALeaveRequest;
import com.cs319group3.backend.Entities.RequestEntities.TAWorkloadRequest;
import com.cs319group3.backend.Repositories.*;
import com.cs319group3.backend.Services.RequestService;
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




    @Override
    public boolean respondToRequest(int requestId, boolean response) {
        Optional<Request> request = requestRepo.findByRequestId(requestId);
        if (request.isEmpty()) {
            return false;
        }
        request.get().setIsApproved(response);
        request.get().setResponseDate(LocalDateTime.now());
        requestRepo.save(request.get());

        Notification notification = new Notification();
        notification.setRequest(request.get());
        notification.setNotificationType(APPROVAL);
        notification.setRead(false);
        notification.setReceiver(request.get().getSenderUser());
        notificationRepo.save(notification);

        return true;
    }

    @Override
    public List<RequestDTO> getRequestsByReceiverUser(int userId) {
        List<Request> requests = requestRepo.findByReceiverUser_UserId(userId);
        List<RequestDTO> requestDTOs = new ArrayList<>();
        for (Request request : requests) {
            requestDTOs.add(RequestMapper.essentialMapper(request));
        }
        return requestDTOs;
    }

    @Override
    public List<RequestDTO> getRequestsBySenderUser(int userId) {
        List<Request> requests = requestRepo.findBySenderUser_UserId(userId);
        List<RequestDTO> requestDTOs = new ArrayList<>();
        for (Request request : requests) {
            requestDTOs.add(RequestMapper.essentialMapper(request));
        }
        return requestDTOs;
    }
}
