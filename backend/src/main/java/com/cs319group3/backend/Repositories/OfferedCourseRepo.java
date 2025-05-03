package com.cs319group3.backend.Repositories;

import com.cs319group3.backend.Entities.Course;
import com.cs319group3.backend.Entities.OfferedCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface OfferedCourseRepo extends JpaRepository<OfferedCourse, Integer> {

    @Query("""
    SELECT r.course.offeredCourseId
    FROM CourseInstructorRelation r
    WHERE r.instructor.userId = :instructorId
""")
    List<Integer> findOfferedCourseIdByInstructorId(int instructorId);

    List<OfferedCourse> findByCourse_CourseId(int courseCourseId);
    Optional<OfferedCourse> findByOfferedCourseIdAndSemester_SemesterId(int offeredCourseId, int semesterId);
}
