package com.cs319group3.backend.DTOs;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OfferedCourseDTO {
    private CourseDTO course;
    private int semesterId;
    private int sectionNo;
    private int offeredCourseId;
}
