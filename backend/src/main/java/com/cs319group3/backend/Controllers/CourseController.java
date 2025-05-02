package com.cs319group3.backend.Controllers;

import com.cs319group3.backend.DTOs.CourseDTO;
import com.cs319group3.backend.Services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping("getCoursesInDepartment")
    public List<CourseDTO> getCourses(@RequestParam int departmentId) {
        System.out.println("getCourses");
        return courseService.getCoursesByDepartmentId(departmentId);
    }
}
