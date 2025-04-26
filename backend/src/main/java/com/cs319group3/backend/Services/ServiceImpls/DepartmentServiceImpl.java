package com.cs319group3.backend.Services.ServiceImpls;

import com.cs319group3.backend.DTOMappers.DepartmentMapper;
import com.cs319group3.backend.DTOs.DepartmentDTO;
import com.cs319group3.backend.Entities.Department;
import com.cs319group3.backend.Repositories.DepartmentRepo;
import com.cs319group3.backend.Services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    DepartmentRepo departmentRepo;

    @Override
    public List<DepartmentDTO> getAllDepartmentsInFaculty(int facultyId) {
        List<Department> allDepartments = departmentRepo.findByFacultyFacultyId(facultyId);
        List<DepartmentDTO> departmentDTOList = new ArrayList<>();
        for (Department department : allDepartments) {
            DepartmentDTO departmentDTO = DepartmentMapper.essentialMapper(department);
            departmentDTOList.add(departmentDTO);
        }
        return departmentDTOList;
    }
}
