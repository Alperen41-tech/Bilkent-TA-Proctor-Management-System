package com.cs319group3.backend.Services;

import com.cs319group3.backend.DTOs.DepartmentDTO;

import java.util.List;

public interface DepartmentService {

    /**
     * Fetches all departments within a given faculty.
     *
     * @param facultyId the ID of the faculty
     * @return list of departments belonging to the specified faculty
     */
    List<DepartmentDTO> getAllDepartmentsInFaculty(int facultyId);

    /**
     * Fetches all departments within a given faculty, excluding the specified department.
     *
     * @param facultyId    the ID of the faculty
     * @param departmentId the department to exclude from the result
     * @return list of departments except the one specified
     */
    List<DepartmentDTO> getAllDepartmentsExcept(int facultyId, int departmentId);

    /**
     * Creates a new department based on the provided DTO.
     *
     * @param dto the department details to create
     * @return true if creation was successful, false otherwise
     */
    boolean createDepartment(DepartmentDTO dto);
}