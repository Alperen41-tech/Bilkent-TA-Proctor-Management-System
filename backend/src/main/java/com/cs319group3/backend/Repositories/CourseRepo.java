package com.cs319group3.backend.Repositories;

import com.cs319group3.backend.Entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CourseRepo extends JpaRepository<Course, Integer> {
    Optional<Course> findByCourseName(String name);
    Optional<Course> findByCourseId(int id);
    List<Course> findByDepartmentDepartmentId(int departmentId);

    @Query("""
    SELECT oc.course.courseId
    FROM User u
    JOIN Student s ON u.bilkentId = s.bilkentId
    JOIN CourseStudentRelation csr ON s.studentId = csr.student.studentId
    JOIN OfferedCourse oc ON csr.course.offeredCourseId = oc.offeredCourseId
    WHERE u.userId = :userId
""")
    List<Integer> findAllCourseIdsByUserId(int userId);

    @Query("SELECT c.courseCode FROM Course c WHERE c.courseId = :courseId")
    Optional<Integer> findCourseCodeByCourseId(int courseId);

    Optional<Course> findByCourseCode(Integer courseCode);

    Optional<Course> findByDepartment_DepartmentCodeAndCourseCode(String departmentCode, int courseCode);
}
