package com.cs319group3.backend.Services;

import com.cs319group3.backend.DTOs.TaskTypeDTO;

import java.util.List;

public interface TaskTypeService {

    /**
     * Creates a new task type for the given course.
     *
     * @param dto      the task type data transfer object containing the name and other details
     * @param courseId the ID of the course the task type is associated with
     * @return true if creation is successful, false otherwise
     */
    boolean createTaskType(TaskTypeDTO dto, int courseId);

    /**
     * Deletes a task type by its name for a given course.
     *
     * @param courseId      the ID of the course
     * @param taskTypeName  the name of the task type to be deleted
     * @return true if deletion is successful, false otherwise
     */
    boolean deleteTaskType(int courseId, String taskTypeName);

    /**
     * Retrieves the list of task type names associated with a course.
     *
     * @param courseId the ID of the course
     * @return list of task type names
     */
    List<String> getTaskTypeNames(int courseId);
}