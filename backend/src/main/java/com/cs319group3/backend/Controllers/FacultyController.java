package com.cs319group3.backend.Controllers;

import com.cs319group3.backend.DTOs.FacultyDTO;
import com.cs319group3.backend.Entities.Faculty;
import com.cs319group3.backend.Services.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("faculty")
public class FacultyController {

    @Autowired
    FacultyService facultyService;

    @GetMapping("getAllFaculty")
    public List<FacultyDTO> getAllFaculty() {
        System.out.println("getting all faculty");
        return facultyService.getAllFaculty();
    }

    @GetMapping("createFaculty")
    public boolean createFaculty(@RequestParam String facultyName) {
        System.out.println("creating faculty");
        return facultyService.createFaculty(facultyName);
    }
}
