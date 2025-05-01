package com.cs319group3.backend.Services.ServiceImpls.RequestServiceImpls;

import com.cs319group3.backend.DTOMappers.RequestMappers.RequestMapper;
import com.cs319group3.backend.DTOs.RequestDTOs.RequestDTO;
import com.cs319group3.backend.Entities.RequestEntities.TALeaveRequest;
import com.cs319group3.backend.Repositories.DepartmentSecretaryRepo;
import com.cs319group3.backend.Repositories.NotificationRepo;
import com.cs319group3.backend.Repositories.TALeaveRequestRepo;
import com.cs319group3.backend.Repositories.TARepo;
import com.cs319group3.backend.Services.NotificationService;
import com.cs319group3.backend.Services.TALeaveRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.cs319group3.backend.Enums.NotificationType.REQUEST;

@Service
public class TALeaveRequestServiceImpl implements TALeaveRequestService {

    @Autowired
    private TARepo taRepo;

    @Autowired
    private DepartmentSecretaryRepo departmentSecretaryRepo;

    @Autowired
    private TALeaveRequestRepo tALeaveRequestRepo;

    @Autowired
    private NotificationRepo notificationRepo;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private RequestMapper requestMapper;

    @Override
    public boolean createTALeaveRequest(RequestDTO taLeaveRequestDTO, int taId) {
        try{
            TALeaveRequest taLeaveRequest = requestMapper.taLeaveRequestToEntityMapper(taLeaveRequestDTO);
            tALeaveRequestRepo.save(taLeaveRequest);

            notificationService.createNotification(taLeaveRequest, REQUEST);
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
