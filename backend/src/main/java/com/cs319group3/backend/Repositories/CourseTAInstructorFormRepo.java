package com.cs319group3.backend.Repositories;

import com.cs319group3.backend.Entities.CourseTAInstructorForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseTAInstructorFormRepo extends JpaRepository<CourseTAInstructorForm, Integer> {

    Optional<CourseTAInstructorForm> findByCourse_CourseId(Integer integer);
}
