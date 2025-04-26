package com.cs319group3.backend.Services;

import com.cs319group3.backend.DTOs.DepartmentDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


public interface DepartmentService {
    List<DepartmentDTO> getAllDepartmentsInFaculty(int facultyId);
}
