package com.cs319group3.backend.Services;

import com.cs319group3.backend.DTOs.CreateTADTO;
import com.cs319group3.backend.DTOs.TAProfileDTO;

import java.util.List;

public interface TAService {
    public TAProfileDTO getTAProfileById(int id);
    public boolean createNewTA(CreateTADTO createTADTO);
    public List<TAProfileDTO> getAllAvailableTAsByDepartmentCode(String departmentCode, int proctoringId);
    public List<TAProfileDTO> getAllAvailableTAsByFacultyId(int facultyId, int proctoringId);
}