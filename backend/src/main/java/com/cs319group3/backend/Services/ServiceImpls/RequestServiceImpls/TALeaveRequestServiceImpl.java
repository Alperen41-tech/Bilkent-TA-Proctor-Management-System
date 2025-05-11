package com.cs319group3.backend.Services.ServiceImpls.RequestServiceImpls;

import com.cs319group3.backend.DTOMappers.RequestMappers.RequestMapper;
import com.cs319group3.backend.DTOs.RequestDTOs.RequestDTO;
import com.cs319group3.backend.Entities.RequestEntities.TALeaveRequest;
import com.cs319group3.backend.Enums.LogType;
import com.cs319group3.backend.Repositories.DepartmentSecretaryRepo;
import com.cs319group3.backend.Repositories.NotificationRepo;
import com.cs319group3.backend.Repositories.TALeaveRequestRepo;
import com.cs319group3.backend.Repositories.TARepo;
import com.cs319group3.backend.Services.LogService;
import com.cs319group3.backend.Services.NotificationService;
import com.cs319group3.backend.Services.TALeaveRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.cs319group3.backend.Enums.NotificationType.REQUEST;

@Service
public class TALeaveRequestServiceImpl implements TALeaveRequestService {

    @Autowired
    private TALeaveRequestRepo tALeaveRequestRepo;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private RequestMapper requestMapper;

    @Autowired
    private LogService logService;

    @Override
    public boolean createTALeaveRequest(RequestDTO taLeaveRequestDTO, int taId) throws Exception {
        taLeaveRequestDTO.setSenderId(taId);
        TALeaveRequest taLeaveRequest = requestMapper.taLeaveRequestToEntityMapper(taLeaveRequestDTO);
        String logMessage = "User " + taLeaveRequest.getSenderUser().getUserId() + " sent a leave request (" +
                taLeaveRequest.getRequestId() + ") to user " + taLeaveRequest.getReceiverUser().getUserId() + ".";
        logService.createLog(logMessage, LogType.CREATE);
        tALeaveRequestRepo.save(taLeaveRequest);

        String description = "You received a leave request from " + taLeaveRequest.getSenderUser().getUserId();
        notificationService.createNotification(taLeaveRequest, REQUEST, description);
        return true;
    }
}
