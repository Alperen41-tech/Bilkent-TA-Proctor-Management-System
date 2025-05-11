package com.cs319group3.backend.Services.ServiceImpls.RequestServiceImpls;

import com.cs319group3.backend.DTOMappers.RequestMappers.RequestMapper;
import com.cs319group3.backend.DTOs.RequestDTOs.RequestDTO;
import com.cs319group3.backend.Entities.*;
import com.cs319group3.backend.Entities.RelationEntities.CourseInstructorRelation;
import com.cs319group3.backend.Entities.RequestEntities.TAWorkloadRequest;
import com.cs319group3.backend.Entities.UserEntities.DepartmentSecretary;
import com.cs319group3.backend.Entities.UserEntities.TA;
import com.cs319group3.backend.Entities.UserEntities.User;
import com.cs319group3.backend.Enums.LogType;
import com.cs319group3.backend.Enums.NotificationType;
import com.cs319group3.backend.Repositories.*;
import com.cs319group3.backend.Services.LogService;
import com.cs319group3.backend.Services.NotificationService;
import com.cs319group3.backend.Services.TAWorkloadRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

import static com.cs319group3.backend.Enums.NotificationType.REQUEST;

@Service
public class TAWorkloadRequestServiceImpl implements TAWorkloadRequestService {

    @Autowired private TARepo taRepo;

    @Autowired private TaskTypeRepo taskTypeRepo;

    @Autowired private TAWorkloadRequestRepo taWorkloadRequestRepo;

    @Autowired private RequestMapper requestMapper;

    @Autowired private NotificationService notificationService;

    @Autowired private OfferedCourseRepo offeredCourseRepo;

    @Autowired private DepartmentSecretaryRepo departmentSecretaryRepo;

    @Autowired private LogService logService;

    @Override
    public boolean createTAWorkloadRequest(RequestDTO dto, int taId) throws Exception {

        Integer workloadId = taWorkloadRequestRepo.getMaxWorkloadId();
        if (workloadId == null) workloadId = 0;
        workloadId++;
        dto.setWorkloadId(workloadId);
        dto.setSenderId(taId);

        Optional<TA> taOptional = taRepo.findById(taId);
        if (taOptional.isEmpty()) throw new RuntimeException("No such TA");

        if (!dto.getTaskTypeName().equals("Other")) {
            Optional<TaskType> taskType = taskTypeRepo.findByTaskTypeNameAndCourse_CourseId(
                    dto.getTaskTypeName(),
                    taOptional.get().getAssignedCourse().getCourseId()
            );
            if (taskType.isEmpty()) throw new RuntimeException("No such task type");
            if (dto.getTimeSpent() > taskType.get().getTimeLimit()){
                System.out.println(dto.getTimeSpent()+" - "+taskType.get().getTimeLimit());
                throw new RuntimeException("Proctoring time exceeds allowed time limit");
            }
        }

        if (!dto.getTaskTypeName().equals("Other")) {
            Optional<TaskType> taskType = taskTypeRepo.findByTaskTypeNameAndCourse_CourseId(
                    dto.getTaskTypeName(),
                    taOptional.get().getAssignedCourse().getCourseId()
            );
            if (taskType.isEmpty()) throw new RuntimeException("No such task type");
            if (dto.getTimeSpent() > taskType.get().getTimeLimit())
                throw new RuntimeException("Proctoring time exceeds allowed time limit");
        }

        List<OfferedCourse> offeredCourses =
                offeredCourseRepo.findByCourse_CourseId(taOptional.get().getAssignedCourse().getCourseId());

        List<User> receivers = new ArrayList<>();
        Set<User> uniqueUsers = new HashSet<>();

        for (OfferedCourse offeredCourse : offeredCourses) {
            for (CourseInstructorRelation relation : offeredCourse.getInstructors()) {
                if (uniqueUsers.add(relation.getInstructor())) {
                    receivers.add(relation.getInstructor());
                }
            }
        }

        Optional<DepartmentSecretary> depsec =
                departmentSecretaryRepo.findByDepartmentDepartmentId(
                        taOptional.get().getDepartment().getDepartmentId()
                );
        depsec.ifPresent(receivers::add);

        for (User receiver : receivers) {
            TAWorkloadRequest workloadRequest = requestMapper.taWorkloadRequestToEntityMapper(dto, receiver);
            taWorkloadRequestRepo.save(workloadRequest);

            String logMessage = "User " + workloadRequest.getSenderUser().getUserId()
                    + " sent a workload request (" + workloadRequest.getRequestId()
                    + ") to user " + workloadRequest.getReceiverUser().getUserId() + ".";
            logService.createLog(logMessage, LogType.CREATE);
            String description = "You received a workload request from " + workloadRequest.getSenderUser().getFullName();
            notificationService.createNotification(workloadRequest, REQUEST, description);
        }

        return true;
    }

    @Override
    public List<RequestDTO> getTAWorkloadRequestsByTA(int taId) {
        List<TAWorkloadRequest> requests = taWorkloadRequestRepo.findBySenderUser_UserId(taId);
        List<TAWorkloadRequest> uniqueRequests = new ArrayList<>();
        Set<Integer> seenWorkloadIds = new HashSet<>();

        for (TAWorkloadRequest req : requests) {
            if (seenWorkloadIds.add(req.getWorkloadId())) {
                uniqueRequests.add(req);
            }
        }

        return requestMapper.taWorkloadRequestMapper(uniqueRequests);
    }

    @Override
    public List<RequestDTO> getTAWorkloadRequestsByInstructor(int instructorId) {
        List<TAWorkloadRequest> requests = taWorkloadRequestRepo.findByReceiverUser_UserId(instructorId);
        return requestMapper.taWorkloadRequestMapper(requests);
    }

    @Override
    public int getTotalWorkload(int taId) {
        List<TAWorkloadRequest> requests = taWorkloadRequestRepo.findBySenderUser_UserId(taId);
        return requests.stream()
                .filter(Objects::nonNull)
                .filter(TAWorkloadRequest::isApproved)
                .mapToInt(TAWorkloadRequest::getTimeSpent)
                .sum();
    }
}