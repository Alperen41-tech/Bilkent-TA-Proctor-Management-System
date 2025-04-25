package com.cs319group3.backend.Controllers;

import com.cs319group3.backend.DTOs.TaskTypeDTO;
import com.cs319group3.backend.Services.TaskTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("taskType")
@CrossOrigin(origins = "http://localhost:3000")
public class TaskTypeController {

    @Autowired
    private TaskTypeService taskTypeService;

    @PostMapping("createTaskType")
    public boolean createTaskType(@RequestBody TaskTypeDTO dto, @RequestParam int courseId) {
        System.out.println("Creating task type.");
        return taskTypeService.createTaskType(dto, courseId);
    }

    @DeleteMapping("deleteTaskType")
    public boolean deleteTaskType(@RequestParam int courseId, @RequestParam String taskTypeName) {
        System.out.println("Deleting task type.");
        return taskTypeService.deleteTaskType(courseId, taskTypeName);
    }

    @GetMapping("getTaskTypeNames")
    public List<String> getTaskTypeNames(@RequestParam int courseId) {
        System.out.println("Extracting task type names.");
        return taskTypeService.getTaskTypeNames(courseId);
    }
}
