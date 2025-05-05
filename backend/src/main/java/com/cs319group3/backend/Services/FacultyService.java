package com.cs319group3.backend.Services;

import com.cs319group3.backend.DTOs.FacultyDTO;

import java.util.List;

public interface FacultyService {
    List<FacultyDTO> getAllFaculty();
    boolean createFaculty(String facultyName);
}
