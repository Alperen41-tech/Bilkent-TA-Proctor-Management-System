package com.cs319group3.backend.Controllers;

import com.cs319group3.backend.Components.CurrentUserUtil;
import com.cs319group3.backend.DTOs.CourseTAInstructorFormDTO;
import com.cs319group3.backend.Services.CourseTAInstructorFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller responsible for handling the submission of TA-Instructor evaluation forms
 * for specific courses.
 */
@RestController
@RequestMapping("courseTAInstructorForm")
@CrossOrigin(origins = "http://localhost:3000")
public class CourseTAInstructorFormController {

    @Autowired
    private CourseTAInstructorFormService courseTAInstructorFormService;
    @Autowired
    private CurrentUserUtil currentUserUtil;

    /**
     * Creates a new TA-Instructor course form submission.
     *
     * @param form the form data submitted by the instructor
     * @return a ResponseEntity indicating success or failure
     */
    @PostMapping("create")
    public ResponseEntity<Boolean> createForm(@RequestBody CourseTAInstructorFormDTO form) {
        int userId = currentUserUtil.getCurrentUserId();
        return courseTAInstructorFormService.createForm(form, userId);
    }
}