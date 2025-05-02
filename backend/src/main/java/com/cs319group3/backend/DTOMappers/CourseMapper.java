package com.cs319group3.backend.DTOMappers;

import com.cs319group3.backend.DTOs.CourseDTO;
import com.cs319group3.backend.Entities.Course;

public class CourseMapper {
    public static CourseDTO toCourseDTO(Course course) {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setCourseCode(course.getCourseCode());
        courseDTO.setName(course.getCourseName());
        courseDTO.setDepartmentCode(course.getDepartment().getDepartmentCode());
        courseDTO.setId(course.getCourseId());
        return courseDTO;
    }
}
