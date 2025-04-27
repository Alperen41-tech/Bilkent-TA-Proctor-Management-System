package com.cs319group3.backend.Services.ServiceImpls;

import com.cs319group3.backend.DTOMappers.TAWorkloadRequestMapper;
import com.cs319group3.backend.DTOs.TAWorkloadRequestDTO;
import com.cs319group3.backend.Entities.Notification;
import com.cs319group3.backend.Entities.RequestEntities.TAWorkloadRequest;
import com.cs319group3.backend.Entities.TaskType;
import com.cs319group3.backend.Entities.UserEntities.TA;
import com.cs319group3.backend.Enums.NotificationType;
import com.cs319group3.backend.Repositories.NotificationRepo;
import com.cs319group3.backend.Repositories.TARepo;
import com.cs319group3.backend.Repositories.TAWorkloadRequestRepo;
import com.cs319group3.backend.Repositories.TaskTypeRepo;
import com.cs319group3.backend.Services.TAWorkloadRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Override
    public boolean createTAWorkloadRequest(TAWorkloadRequestDTO dto, int taId) {
        TAWorkloadRequest taWorkloadRequest = new TAWorkloadRequest();
        Optional<TA> ta = taRepo.findById(taId);
        if (ta.isEmpty()) {
            return false;
        }
        Optional<TaskType> taskType = taskTypeRepo.findByTaskTypeNameAndCourse_CourseId(dto.getTaskTypeName(), ta.get().getAssignedCourse().getCourseId());
        if (taskType.isEmpty()) {
            return false;
        }
        taWorkloadRequest.setTaskType(taskType.get());
        taWorkloadRequest.setCourse(taskType.get().getCourse());
        taWorkloadRequest.setTimeSpent(dto.getTimeSpent());
        taWorkloadRequest.setDescription(dto.getDescription());
        taWorkloadRequest.setSenderUser(ta.get());
        taWorkloadRequest.setSentDate(LocalDateTime.now());
        taWorkloadRequest.setReceiverUser(ta.get().getAssignedCourse().getCoordinator());
        taWorkloadRequestRepo.save(taWorkloadRequest);

        Notification notification = new Notification();
        notification.setRequest(taWorkloadRequest);
        notification.setNotificationType(REQUEST);
        notification.setRead(false);
        notification.setReceiver(taWorkloadRequest.getReceiverUser());
        notificationRepo.save(notification);

        return true;
    }

    @Override
    public List<TAWorkloadRequestDTO> getTAWorkloadRequestsByTA(int taId) {
        List<TAWorkloadRequest> requests = taWorkloadRequestRepo.findBySenderUser_UserId(taId);
        List<TAWorkloadRequestDTO> dtos = new ArrayList<>();
        for (TAWorkloadRequest request : requests) {
            dtos.add(TAWorkloadRequestMapper.essentialMapper(request));
        }
        return dtos;
    }

    @Override
    public List<TAWorkloadRequestDTO> getTAWorkloadRequestsByInstructor(int instructorId) {
        List<TAWorkloadRequest> requests = taWorkloadRequestRepo.findByReceiverUser_UserId(instructorId);
        List<TAWorkloadRequestDTO> dtos = new ArrayList<>();
        for (TAWorkloadRequest request : requests) {
            dtos.add(TAWorkloadRequestMapper.essentialMapper(request));
            dtos.get(dtos.size()-1).setTaName(request.getSenderUser().getName() + " " + request.getSenderUser().getSurname());
            dtos.get(dtos.size()-1).setTaMail(request.getSenderUser().getEmail());
        }
        return dtos;
    }

    @Override
    public int getTotalWorkload(int taId) {
        List<TAWorkloadRequest> requests = taWorkloadRequestRepo.findBySenderUser_UserId(taId);
        int sum = 0;
        for(TAWorkloadRequest request : requests) {
            if (request != null && request.getIsApproved()) {
                sum += request.getTimeSpent();
            }
        }
        return sum;
    }

}
