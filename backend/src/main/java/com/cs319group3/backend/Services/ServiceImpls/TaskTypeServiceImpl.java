package com.cs319group3.backend.Services.ServiceImpls;

import com.cs319group3.backend.DTOMappers.TaskTypeMapper;
import com.cs319group3.backend.DTOs.TaskTypeDTO;
import com.cs319group3.backend.Entities.RequestEntities.TAWorkloadRequest;
import com.cs319group3.backend.Entities.TaskType;
import com.cs319group3.backend.Repositories.CourseRepo;
import com.cs319group3.backend.Repositories.TAWorkloadRequestRepo;
import com.cs319group3.backend.Repositories.TaskTypeRepo;
import com.cs319group3.backend.Services.TaskTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskTypeServiceImpl implements TaskTypeService {

    @Autowired
    private CourseRepo courseRepo;

    @Autowired
    private TaskTypeRepo taskTypeRepo;

    @Autowired
    private TAWorkloadRequestRepo taWorkloadRequestRepo;

    @Override
    public boolean createTaskType(TaskTypeDTO dto, int courseId) {
        if (taskTypeRepo.findByTaskTypeNameAndCourse_CourseId(dto.getTaskTypeName(), courseId).isPresent()) {
            return false;
        }

        TaskType taskType = TaskTypeMapper.essentialMapper(dto);
        taskType.setCourse(courseRepo.findByCourseId(courseId).get());
        taskTypeRepo.save(taskType);
        return true;
    }

    @Override
    public boolean deleteTaskType(int courseId, String taskTypeName) {
        List<TAWorkloadRequest> requests = taWorkloadRequestRepo.findByTaskType_TaskTypeName(taskTypeName);
        if (!requests.isEmpty()) {
            throw new RuntimeException("Task type " + taskTypeName + " already in use");
        }

        if (taskTypeRepo.findByTaskTypeNameAndCourse_CourseId(taskTypeName, courseId).isPresent()) {
            TaskType taskType = taskTypeRepo.findByTaskTypeNameAndCourse_CourseId(taskTypeName, courseId).get();
            taskTypeRepo.delete(taskType);
            return true;
        } else {
            throw new RuntimeException("Task type with name " + taskTypeName + " not found.");
        }
    }

    @Override
    public List<String> getTaskTypeNames(int courseId) {
        return taskTypeRepo.findTaskTypeNamesByCourseId(courseId);
    }
}