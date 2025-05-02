package com.cs319group3.backend.Repositories;

import com.cs319group3.backend.Entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRepo extends JpaRepository<Course, Integer> {
    Optional<Course> findByCourseName(String name);
    Optional<Course> findByCourseId(int id);
    List<Course> findByDepartmentDepartmentId(int departmentId);
}
