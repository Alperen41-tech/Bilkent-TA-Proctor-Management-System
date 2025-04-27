package com.cs319group3.backend.Controllers;

import com.cs319group3.backend.DTOs.DepartmentDTO;
import com.cs319group3.backend.Entities.Department;
import com.cs319group3.backend.Services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("department")
@CrossOrigin(origins = "http://localhost:3000")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @GetMapping("getAllDepartmentsInFaculty")
    public List<DepartmentDTO> getAllDepartmentsInFaculty(@RequestParam int facultyId) {
        System.out.println("getting all departments in faculty " + facultyId);
        return departmentService.getAllDepartmentsInFaculty(facultyId);
    }

    @GetMapping("getAllDepartmentsExcept")
    public List<DepartmentDTO> getAllDepartmentsExcept(@RequestParam int facultyId, @RequestParam int departmentId) {
        System.out.println("getting all departments except " + departmentId);
        return departmentService.getAllDepartmentsExcept(facultyId, departmentId);
    }
}
