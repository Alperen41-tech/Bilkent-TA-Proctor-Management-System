package com.cs319group3.backend.Services;

import com.cs319group3.backend.DTOs.TaskTypeDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


public interface TaskTypeService {
    public boolean createTaskType(TaskTypeDTO dto, int courseId);
    public boolean deleteTaskType(@RequestParam int courseId, @RequestParam String taskTypeName);
    public List<String> getTaskTypeNames(int courseId);
}
