package com.cs319group3.backend.Controllers;

import com.cs319group3.backend.DTOs.DepartmentDTO;
import com.cs319group3.backend.Services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsible for managing department-related operations such as
 * creation and retrieval based on faculty and exclusions.
 */
@RestController
@RequestMapping("department")
@CrossOrigin(origins = "http://localhost:3000")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    /**
     * Retrieves all departments within a specified faculty.
     *
     * @param facultyId the ID of the faculty
     * @return a list of DepartmentDTOs in that faculty
     */
    @GetMapping("getAllDepartmentsInFaculty")
    public List<DepartmentDTO> getAllDepartmentsInFaculty(@RequestParam int facultyId) {
        System.out.println("getting all departments in faculty " + facultyId);
        return departmentService.getAllDepartmentsInFaculty(facultyId);
    }

    /**
     * Retrieves all departments in the faculty except the specified department.
     *
     * @param facultyId the faculty ID
     * @param departmentId the department ID to exclude
     * @return a filtered list of DepartmentDTOs
     */
    @GetMapping("getAllDepartmentsExcept")
    public List<DepartmentDTO> getAllDepartmentsExcept(@RequestParam int facultyId, @RequestParam int departmentId) {
        System.out.println("getting all departments except " + departmentId);
        return departmentService.getAllDepartmentsExcept(facultyId, departmentId);
    }

    /**
     * Creates a new department.
     *
     * @param dto the department data
     * @return true if creation was successful
     */
    @PostMapping("createDepartment")
    public boolean createDepartment(@RequestBody DepartmentDTO dto) {
        System.out.println("creating department");
        return departmentService.createDepartment(dto);
    }
}