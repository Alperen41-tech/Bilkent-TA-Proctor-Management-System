package com.cs319group3.backend.Services.ServiceImpls.RequestServiceImpls;

import com.cs319group3.backend.DTOMappers.RequestMappers.RequestMapper;
import com.cs319group3.backend.DTOs.RequestDTOs.RequestDTO;
import com.cs319group3.backend.Entities.Course;
import com.cs319group3.backend.Entities.OfferedCourse;
import com.cs319group3.backend.Entities.RelationEntities.CourseInstructorRelation;
import com.cs319group3.backend.Entities.RequestEntities.TASwapRequest;
import com.cs319group3.backend.Entities.RequestEntities.TAWorkloadRequest;
import com.cs319group3.backend.Entities.TaskType;
import com.cs319group3.backend.Entities.UserEntities.DepartmentSecretary;
import com.cs319group3.backend.Entities.UserEntities.Instructor;
import com.cs319group3.backend.Entities.UserEntities.TA;
import com.cs319group3.backend.Entities.UserEntities.User;
import com.cs319group3.backend.Enums.NotificationType;
import com.cs319group3.backend.Repositories.*;
import com.cs319group3.backend.Services.NotificationService;
import com.cs319group3.backend.Services.TAWorkloadRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

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
    @Autowired
    private CourseRepo courseRepo;
    @Autowired
    private OfferedCourseRepo offeredCourseRepo;
    @Autowired
    private DepartmentSecretaryRepo departmentSecretaryRepo;

    @Override
    public boolean createTAWorkloadRequest(RequestDTO dto, int taId) {


        try {
            Integer workloadId = taWorkloadRequestRepo.getMaxWorkloadId();
            if (workloadId == null) {
                workloadId = 0;
            }
            workloadId++;
            dto.setWorkloadId(workloadId);
            dto.setSenderId(taId);

            Optional<TA> taOptional = taRepo.findById(taId);
            if (taOptional.isEmpty()) {
                throw new RuntimeException("No such TA");
            }
            List<OfferedCourse> offeredCourses = offeredCourseRepo.findByCourse_CourseId(taOptional.get().getAssignedCourse().getCourseId());
            List<User> receivers = new ArrayList<>();
            Set<User> users = new HashSet<>();
            for (OfferedCourse offeredCourse : offeredCourses) {
                List<CourseInstructorRelation> relations = offeredCourse.getInstructors();
                for (CourseInstructorRelation relation : relations) {
                    if (!users.contains(relation.getInstructor())) {
                        users.add(relation.getInstructor());
                        receivers.add(relation.getInstructor());
                    }
                }
            }
            Optional<DepartmentSecretary> depsec = departmentSecretaryRepo.findByDepartmentDepartmentId(taOptional.get().getDepartment().getDepartmentId());
            if (depsec.isEmpty()) {
                throw new RuntimeException("No such department secretary");
            }
            receivers.add(depsec.get());


            for (User receiver : receivers) {
                TAWorkloadRequest workloadRequest = requestMapper.taWorkloadRequestToEntityMapper(dto, receiver);
                taWorkloadRequestRepo.save(workloadRequest);
                notificationService.createNotification(workloadRequest, NotificationType.REQUEST);
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace(); // for debugging, or better, log it properly
            return false;
        }
    }

    @Override
    public List<RequestDTO> getTAWorkloadRequestsByTA(int taId) {
        List<TAWorkloadRequest> requests = taWorkloadRequestRepo.findBySenderUser_UserId(taId);
        List<TAWorkloadRequest> workloadRequests = new ArrayList<>();
        Set<Integer> workloadIdSet = new HashSet<>();
        for (TAWorkloadRequest taWorkloadRequest : requests) {
            if (!workloadIdSet.contains(taWorkloadRequest.getWorkloadId())) {
                workloadIdSet.add(taWorkloadRequest.getWorkloadId());
                workloadRequests.add(taWorkloadRequest);
            }
        }
        return requestMapper.taWorkloadRequestMapper(workloadRequests);
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
