package com.cs319group3.backend.Controllers;

import com.cs319group3.backend.DTOs.FacultyDTO;
import com.cs319group3.backend.Services.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsible for managing faculty operations such as
 * retrieval and creation.
 */
@RestController
@RequestMapping("faculty")
public class FacultyController {

    @Autowired
    private FacultyService facultyService;

    /**
     * Retrieves all faculty records in the system.
     *
     * @return a list of FacultyDTOs
     */
    @GetMapping("getAllFaculty")
    public List<FacultyDTO> getAllFaculty() {
        System.out.println("getting all faculty");
        return facultyService.getAllFaculty();
    }

    /**
     * Creates a new faculty record with the given name.
     *
     * @param facultyName the name of the faculty to create
     * @return true if the faculty was created successfully
     */
    @GetMapping("createFaculty")
    public boolean createFaculty(@RequestParam String facultyName) {
        System.out.println("creating faculty");
        return facultyService.createFaculty(facultyName);
    }
}