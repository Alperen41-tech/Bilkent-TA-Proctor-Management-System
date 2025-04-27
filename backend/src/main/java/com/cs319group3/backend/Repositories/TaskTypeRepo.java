package com.cs319group3.backend.Repositories;

import com.cs319group3.backend.Entities.TaskType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskTypeRepo extends JpaRepository<TaskType, Integer> {
    Optional<TaskType> findByTaskTypeNameAndCourse_CourseId(String taskTypeName, int courseId);
    @Query("SELECT t.taskTypeName FROM TaskType t WHERE t.course.courseId = :courseId")
    List<String> findTaskTypeNamesByCourseId(@Param("courseId") int courseId);
}
