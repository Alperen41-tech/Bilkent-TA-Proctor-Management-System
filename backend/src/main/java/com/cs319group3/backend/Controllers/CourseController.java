package com.cs319group3.backend.Controllers;
import com.cs319group3.backend.DTOs.CourseDTO;
import com.cs319group3.backend.DTOs.OfferedCourseDTO;
import com.cs319group3.backend.Services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("getCoursesOfInstructor")
    public List<OfferedCourseDTO> getCoursesOfInstructor(@RequestParam int instructorId) {
        System.out.println("getCoursesOfInstructor");
        return courseService.getCoursesOfInstructor(instructorId);
    }

    @PostMapping("createCourse")
    public boolean createCourse(@RequestBody CourseDTO dto) {
        System.out.println("createCourse");
        return courseService.createCourse(dto);
    }

    @PostMapping("createOfferedCourse")
    public boolean createOfferedCourse(@RequestBody OfferedCourseDTO dto) {
        System.out.println("createOfferedCourse");
        return courseService.createOfferedCourse(dto);
    }
}
