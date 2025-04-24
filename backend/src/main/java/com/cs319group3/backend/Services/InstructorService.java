package com.cs319group3.backend.Services;

import com.cs319group3.backend.DTOs.InstructorProfileDTO;
import com.cs319group3.backend.DTOs.TaskTypeDTO;

public interface InstructorService {

    public InstructorProfileDTO getInstructorProfileById(int id);
    public boolean createTaskType(TaskTypeDTO dto, int courseId);
}
