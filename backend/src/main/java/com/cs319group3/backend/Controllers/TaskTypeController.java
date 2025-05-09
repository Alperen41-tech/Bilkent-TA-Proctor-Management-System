package com.cs319group3.backend.Controllers;

import com.cs319group3.backend.DTOs.TaskTypeDTO;
import com.cs319group3.backend.Services.TaskTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsible for managing task types associated with courses,
 * including creation, deletion, and retrieval of task type names.
 */
@RestController
@RequestMapping("taskType")
@CrossOrigin(origins = "http://localhost:3000")
public class TaskTypeController {

    @Autowired
    private TaskTypeService taskTypeService;

    /**
     * Creates a new task type associated with a specific course.
     *
     * @param dto the task type data
     * @param courseId the ID of the course
     * @return true if creation was successful
     */
    @PostMapping("createTaskType")
    public boolean createTaskType(@RequestBody TaskTypeDTO dto, @RequestParam int courseId) {
        System.out.println("Creating task type.");
        return taskTypeService.createTaskType(dto, courseId);
    }

    /**
     * Deletes a task type associated with a course by name.
     *
     * @param courseId the ID of the course
     * @param taskTypeName the name of the task type to delete
     * @return true if deletion was successful
     */
    @DeleteMapping("deleteTaskType")
    public boolean deleteTaskType(@RequestParam int courseId, @RequestParam String taskTypeName) {
        System.out.println("Deleting task type.");
        return taskTypeService.deleteTaskType(courseId, taskTypeName);
    }

    /**
     * Retrieves the names of all task types for a given course.
     *
     * @param courseId the ID of the course
     * @return list of task type names
     */
    @GetMapping("getTaskTypeNames")
    public List<String> getTaskTypeNames(@RequestParam int courseId) {
        System.out.println("Extracting task type names.");
        return taskTypeService.getTaskTypeNames(courseId);
    }
}