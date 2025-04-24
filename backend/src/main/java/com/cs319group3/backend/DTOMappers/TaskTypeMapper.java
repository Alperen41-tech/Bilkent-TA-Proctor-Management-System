package com.cs319group3.backend.DTOMappers;

import com.cs319group3.backend.DTOs.TaskTypeDTO;
import com.cs319group3.backend.Entities.TaskType;
import com.cs319group3.backend.Repositories.CourseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskTypeMapper {

    public static TaskType essentialMapper(TaskTypeDTO dto) {
        TaskType taskType = new TaskType();
        taskType.setTaskTypeName(dto.getTaskTypeName());
        taskType.setTimeLimit(dto.getTaskLimit());
        return taskType;
    }
}
