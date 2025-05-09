package com.cs319group3.backend.Controllers;

import com.cs319group3.backend.Components.CurrentUserUtil;
import com.cs319group3.backend.DTOs.CourseDTO;
import com.cs319group3.backend.DTOs.OfferedCourseDTO;
import com.cs319group3.backend.Services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsible for course management operations,
 * including creation and listing of courses and offered courses.
 */
@RestController
@RequestMapping("course")
@CrossOrigin(origins = "http://localhost:3000")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CurrentUserUtil currentUserUtil;

    /**
     * Retrieves all courses offered by a specific department.
     *
     * @param departmentId the department ID to filter courses
     * @return a list of CourseDTOs
     */
    @GetMapping("getCoursesInDepartment")
    public List<CourseDTO> getCourses(@RequestParam int departmentId) {
        System.out.println("getCourses");
        return courseService.getCoursesByDepartmentId(departmentId);
    }

    /**
     * Retrieves all offered courses assigned to the currently logged-in instructor.
     *
     * @return a list of OfferedCourseDTOs
     */
    @GetMapping("getCoursesOfInstructor")
    public List<OfferedCourseDTO> getCoursesOfInstructor() {
        int instructorId = currentUserUtil.getCurrentUserId();
        System.out.println("getCoursesOfInstructor");
        return courseService.getCoursesOfInstructor(instructorId);
    }

    /**
     * Creates a new course record.
     *
     * @param dto the course data
     * @return true if creation was successful
     */
    @PostMapping("createCourse")
    public boolean createCourse(@RequestBody CourseDTO dto) {
        System.out.println("createCourse");
        return courseService.createCourse(dto);
    }

    /**
     * Creates a new offered course record.
     *
     * @param dto the offered course data
     * @return true if creation was successful
     */
    @PostMapping("createOfferedCourse")
    public boolean createOfferedCourse(@RequestBody OfferedCourseDTO dto) {
        System.out.println("createOfferedCourse");
        return courseService.createOfferedCourse(dto);
    }

    /**
     * Retrieves all course records in the system.
     *
     * @return a list of CourseDTOs
     */
    @GetMapping("getAllCourses")
    public List<CourseDTO> getAllCourses() {
        return courseService.getAllCourses();
    }
}