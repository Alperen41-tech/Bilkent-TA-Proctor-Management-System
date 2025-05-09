package com.cs319group3.backend.Services;

import com.cs319group3.backend.DTOs.CourseTAInstructorFormDTO;
import org.springframework.http.ResponseEntity;

public interface CourseTAInstructorFormService {

    /**
     * Creates a new Course-TA-Instructor form.
     *
     * @param form the form data containing TA, Instructor, and Course info
     * @return true if successfully created, false otherwise
     */
    ResponseEntity<Boolean> createForm(CourseTAInstructorFormDTO form);
}