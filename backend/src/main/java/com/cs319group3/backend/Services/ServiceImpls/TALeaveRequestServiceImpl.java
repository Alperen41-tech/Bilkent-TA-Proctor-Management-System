package com.cs319group3.backend.Services.ServiceImpls;

import com.cs319group3.backend.DTOs.RequestDTOs.RequestDTO;
import com.cs319group3.backend.Entities.Notification;
import com.cs319group3.backend.Entities.RequestEntities.TALeaveRequest;
import com.cs319group3.backend.Entities.UserEntities.DepartmentSecretary;
import com.cs319group3.backend.Entities.UserEntities.TA;
import com.cs319group3.backend.Repositories.DepartmentSecretaryRepo;
import com.cs319group3.backend.Repositories.NotificationRepo;
import com.cs319group3.backend.Repositories.TALeaveRequestRepo;
import com.cs319group3.backend.Repositories.TARepo;
import com.cs319group3.backend.Services.TALeaveRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.cs319group3.backend.Enums.NotificationType.REQUEST;

@Service
public class TALeaveRequestServiceImpl implements TALeaveRequestService {

    @Autowired
    TARepo taRepo;

    @Autowired
    DepartmentSecretaryRepo departmentSecretaryRepo;

    @Autowired
    TALeaveRequestRepo tALeaveRequestRepo;

    @Autowired
    NotificationRepo notificationRepo;

    @Override
    public boolean createTALeaveRequest(RequestDTO taLeaveRequestDTO, int taId) {
        TALeaveRequest taLeaveRequest = new TALeaveRequest();
        Optional<TA> optionalTA = taRepo.findByUserId(taId);
        if (optionalTA.isEmpty()) {
            return false;
        }
        taLeaveRequest.setDescription(taLeaveRequestDTO.getDescription());
        taLeaveRequest.setSenderUser(optionalTA.get());
        Optional<DepartmentSecretary> optionalDs = departmentSecretaryRepo.findByDepartment_DepartmentId(optionalTA.get().getDepartment().getDepartmentId());
        if (optionalDs.isEmpty()) {
            return false;
        }
        taLeaveRequest.setReceiverUser(optionalDs.get());
        taLeaveRequest.setSentDate(LocalDateTime.now());
        taLeaveRequest.setLeaveStartDate(taLeaveRequestDTO.getLeaveStartDate());
        taLeaveRequest.setLeaveEndDate(taLeaveRequestDTO.getLeaveEndDate());
        tALeaveRequestRepo.save(taLeaveRequest);

        Notification notification = new Notification();
        notification.setRequest(taLeaveRequest);
        notification.setNotificationType(REQUEST);
        notification.setRead(false);
        notification.setReceiver(taLeaveRequest.getReceiverUser());
        notificationRepo.save(notification);

        return true;
    }
}
