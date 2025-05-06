package com.cs319group3.backend.Services;

import com.cs319group3.backend.DTOs.CourseTAInstructorFormDTO;
import org.springframework.http.ResponseEntity;

public interface CourseTAInstructorFormService {


    ResponseEntity<Boolean> createForm(CourseTAInstructorFormDTO form);
}
