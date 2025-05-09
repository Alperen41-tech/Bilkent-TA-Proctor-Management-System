package com.cs319group3.backend.Services.ServiceImpls;

import com.cs319group3.backend.DTOMappers.DepartmentMapper;
import com.cs319group3.backend.DTOs.DepartmentDTO;
import com.cs319group3.backend.Entities.Department;
import com.cs319group3.backend.Entities.Faculty;
import com.cs319group3.backend.Repositories.DepartmentRepo;
import com.cs319group3.backend.Repositories.FacultyRepo;
import com.cs319group3.backend.Services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private FacultyRepo facultyRepo;

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

    @Override
    public List<DepartmentDTO> getAllDepartmentsExcept(int facultyId, int departmentId) {
        List<Department> listOfDepartments = departmentRepo.findByFacultyIdAndDepartmentIdNot(facultyId, departmentId);
        List<DepartmentDTO> departmentDTOList = new ArrayList<>();

        for (Department department : listOfDepartments) {
            DepartmentDTO departmentDTO = DepartmentMapper.essentialMapper(department);
            departmentDTOList.add(departmentDTO);
        }

        return departmentDTOList;
    }

    @Override
    public boolean createDepartment(DepartmentDTO dto) {
        Department department = new Department();
        String departmentCode = dto.getDepartmentCode();
        List<String> listOfCodes = departmentRepo.findAllDepartmentCodes();

        if (listOfCodes.contains(departmentCode)) {
            return false;
        }

        department.setDepartmentCode(departmentCode);
        department.setDepartmentName(dto.getDepartmentName());

        Optional<Faculty> faculty = facultyRepo.findById(dto.getFacultyId());
        if (faculty.isEmpty()) {
            return false;
        }

        department.setFaculty(faculty.get());
        departmentRepo.save(department);

        return true;
    }
}