package com.cs319group3.backend.Controllers;


import com.cs319group3.backend.DTOs.CourseTAInstructorFormDTO;
import com.cs319group3.backend.Entities.CourseTAInstructorForm;
import com.cs319group3.backend.Services.CourseTAInstructorFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("courseTAInstructorForm")
@CrossOrigin(origins = "http://localhost:3000")
public class CourseTAInstructorFormController {


    @Autowired
    private CourseTAInstructorFormService courseTAInstructorFormService;

    @PostMapping("create")
    public ResponseEntity<Boolean> createForm(@RequestBody CourseTAInstructorFormDTO form) {
        return courseTAInstructorFormService.createForm(form);
    }


}
