package com.cs319group3.backend.DTOMappers;

import com.cs319group3.backend.DTOs.FacultyDTO;
import com.cs319group3.backend.Entities.Faculty;

public class FacultyMapper {
    public static FacultyDTO essentialMapper(Faculty faculty) {
        FacultyDTO facultyDTO = new FacultyDTO();
        facultyDTO.setFacultyId(faculty.getFacultyId());
        facultyDTO.setFacultyName(faculty.getFacultyName());
        return facultyDTO;
    }
}
