package com.cs319group3.backend.Services.ServiceImpls.RequestServiceImpls;

import com.cs319group3.backend.DTOMappers.RequestMappers.RequestMapper;
import com.cs319group3.backend.DTOs.RequestDTOs.RequestDTO;
import com.cs319group3.backend.Entities.RequestEntities.TASwapRequest;
import com.cs319group3.backend.Entities.RequestEntities.TAWorkloadRequest;
import com.cs319group3.backend.Entities.TaskType;
import com.cs319group3.backend.Entities.UserEntities.TA;
import com.cs319group3.backend.Enums.NotificationType;
import com.cs319group3.backend.Repositories.NotificationRepo;
import com.cs319group3.backend.Repositories.TARepo;
import com.cs319group3.backend.Repositories.TAWorkloadRequestRepo;
import com.cs319group3.backend.Repositories.TaskTypeRepo;
import com.cs319group3.backend.Services.NotificationService;
import com.cs319group3.backend.Services.TAWorkloadRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.cs319group3.backend.Enums.NotificationType.REQUEST;

@Service
public class TAWorkloadRequestServiceImpl implements TAWorkloadRequestService{

    @Autowired
    private TARepo taRepo;

    @Autowired
    private TaskTypeRepo taskTypeRepo;

    @Autowired
    private TAWorkloadRequestRepo taWorkloadRequestRepo;

    @Autowired
    private NotificationRepo notificationRepo;

    @Autowired
    private RequestMapper requestMapper;

    @Autowired
    private NotificationService notificationService;

    @Override
    public boolean createTAWorkloadRequest(RequestDTO dto, int taId) {


        try {
            dto.setSenderId(taId);
            TAWorkloadRequest workloadRequest = requestMapper.taWorkloadRequestToEntityMapper(dto);
            taWorkloadRequestRepo.save(workloadRequest);
            notificationService.createNotification(workloadRequest, NotificationType.REQUEST);

            return true;
        } catch (Exception e) {
            e.printStackTrace(); // for debugging, or better, log it properly
            return false;
        }
    }

    @Override
    public List<RequestDTO> getTAWorkloadRequestsByTA(int taId) {
        List<TAWorkloadRequest> requests = taWorkloadRequestRepo.findBySenderUser_UserId(taId);
        return requestMapper.taWorkloadRequestMapper(requests);
    }

    @Override
    public List<RequestDTO> getTAWorkloadRequestsByInstructor(int instructorId) {
        List<TAWorkloadRequest> requests = taWorkloadRequestRepo.findByReceiverUser_UserId(instructorId);
        return requestMapper.taWorkloadRequestMapper(requests);
    }

    @Override
    public int getTotalWorkload(int taId) {
        List<TAWorkloadRequest> requests = taWorkloadRequestRepo.findBySenderUser_UserId(taId);
        int sum = 0;
        for(TAWorkloadRequest request : requests) {
            if (request != null && request.isApproved()) {
                sum += request.getTimeSpent();
            }
        }
        return sum;
    }

}
