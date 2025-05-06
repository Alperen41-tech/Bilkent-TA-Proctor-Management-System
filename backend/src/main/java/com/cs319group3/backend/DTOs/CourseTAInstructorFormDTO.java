package com.cs319group3.backend.DTOs;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourseTAInstructorFormDTO {

    // Primitive int parameters
    private int minTALoad;
    private int maxTALoad;
    private int numberOfGrader;

    // String parameter
    private String description;

    // List of strings for TA preferences
    private String mustHaveTAs;
    private String preferredTAs;
    private String preferredGraders;
    private String avoidedTAs;

    // IDs for related entities
    private int instructorId;
    private int courseId;
}
