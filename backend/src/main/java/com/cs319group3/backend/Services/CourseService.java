package com.cs319group3.backend.Services;

import com.cs319group3.backend.DTOs.CourseDTO;
import com.cs319group3.backend.Entities.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<CourseDTO> getCoursesByDepartmentId(int departmentId);
}
