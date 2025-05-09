package com.cs319group3.backend.Services;

import com.cs319group3.backend.DTOs.CourseDTO;
import com.cs319group3.backend.DTOs.OfferedCourseDTO;
import java.util.List;

public interface CourseService {

    /**
     * Retrieves a list of courses offered by a specific department.
     *
     * @param departmentId The ID of the department.
     * @return A list of CourseDTOs belonging to the department.
     */
    List<CourseDTO> getCoursesByDepartmentId(int departmentId);

    /**
     * Retrieves the list of courses taught by a specific instructor.
     *
     * @param instructorId The ID of the instructor.
     * @return A list of OfferedCourseDTOs taught by the instructor.
     */
    List<OfferedCourseDTO> getCoursesOfInstructor(int instructorId);

    /**
     * Creates a new course in the system.
     *
     * @param dto The CourseDTO containing course details.
     * @return true if creation is successful; false otherwise.
     */
    boolean createCourse(CourseDTO dto);

    /**
     * Creates a new offered course (specific to a term or section).
     *
     * @param dto The OfferedCourseDTO containing offered course details.
     * @return true if creation is successful; false otherwise.
     */
    boolean createOfferedCourse(OfferedCourseDTO dto);

    /**
     * Retrieves all courses in the system.
     *
     * @return A list of all CourseDTOs.
     */
    List<CourseDTO> getAllCourses();
}