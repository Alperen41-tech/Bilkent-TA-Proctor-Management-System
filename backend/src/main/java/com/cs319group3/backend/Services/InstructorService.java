package com.cs319group3.backend.Services;

import com.cs319group3.backend.DTOs.CreateInstructorDTO;
import com.cs319group3.backend.DTOs.InstructorProfileDTO;
import com.cs319group3.backend.DTOs.TaskTypeDTO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface InstructorService {

    public InstructorProfileDTO getInstructorProfileById(int id);
    public boolean createInstructor(CreateInstructorDTO ciDTO);
    public List<InstructorProfileDTO> getAllInstructors();
}
