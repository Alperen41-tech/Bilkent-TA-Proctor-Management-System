package com.cs319group3.backend.Services;

import com.cs319group3.backend.DTOs.FacultyDTO;

import java.util.List;

public interface FacultyService {

    /**
     * Retrieves a list of all faculties in the system.
     *
     * @return a list of FacultyDTO objects
     */
    List<FacultyDTO> getAllFaculty();

    /**
     * Creates a new faculty with the given name.
     *
     * @param facultyName the name of the faculty to create
     * @return true if the faculty was created successfully, false otherwise
     */
    boolean createFaculty(String facultyName);
}