package com.cs319group3.backend.Services.ServiceImpls;

import com.cs319group3.backend.DTOMappers.FacultyMapper;
import com.cs319group3.backend.DTOs.FacultyDTO;
import com.cs319group3.backend.Entities.Faculty;
import com.cs319group3.backend.Repositories.FacultyRepo;
import com.cs319group3.backend.Services.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FacultyServiceImpl implements FacultyService {

    @Autowired
    private FacultyRepo facultyRepo;

    @Override
    public List<FacultyDTO> getAllFaculty() {
        List<Faculty> facultyList = facultyRepo.findAll();
        List<FacultyDTO> facultyDTOList = new ArrayList<>();

        for (Faculty faculty : facultyList) {
            facultyDTOList.add(FacultyMapper.essentialMapper(faculty));
        }

        return facultyDTOList;
    }

    @Override
    public boolean createFaculty(String facultyName) {
        Faculty faculty = new Faculty();
        faculty.setFacultyName(facultyName);
        facultyRepo.save(faculty);
        return true;
    }
}