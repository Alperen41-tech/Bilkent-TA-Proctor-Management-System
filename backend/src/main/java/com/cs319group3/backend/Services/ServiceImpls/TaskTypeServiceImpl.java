package com.cs319group3.backend.Services.ServiceImpls;

import com.cs319group3.backend.DTOMappers.TaskTypeMapper;
import com.cs319group3.backend.DTOs.TaskTypeDTO;
import com.cs319group3.backend.Entities.TaskType;
import com.cs319group3.backend.Repositories.CourseRepo;
import com.cs319group3.backend.Repositories.TaskTypeRepo;
import com.cs319group3.backend.Services.TaskTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@Service
public class TaskTypeServiceImpl implements TaskTypeService {

    @Autowired
    CourseRepo courseRepo;

    @Autowired
    TaskTypeRepo taskTypeRepo;

    @Override
    public boolean createTaskType(TaskTypeDTO dto, int courseId) {
        if(taskTypeRepo.findByTaskTypeName(dto.getTaskTypeName()).isPresent()) {
            return false;
        }
        TaskType taskType = TaskTypeMapper.essentialMapper(dto);
        taskType.setCourse(courseRepo.findByCourseId(courseId).get());
        taskTypeRepo.save(taskType);
        return true;
    }

    @Override
    public boolean deleteTaskType(int courseId, @RequestParam String taskTypeName) {
        if(taskTypeRepo.findByTaskTypeName(taskTypeName).isPresent()){
            TaskType taskType = taskTypeRepo.findByTaskTypeName(taskTypeName).get();
            taskTypeRepo.delete(taskType);
            return true;
        }
        else{
            throw new RuntimeException("Task type with name " + taskTypeName + " not found.");
        }
    }

    @Override
    public List<String> getTaskTypeNames(int courseId) {
        return taskTypeRepo.findTaskTypeNamesByCourseId(courseId);
    }

}
