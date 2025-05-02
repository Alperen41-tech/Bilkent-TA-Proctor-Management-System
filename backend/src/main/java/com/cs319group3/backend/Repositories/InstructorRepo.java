package com.cs319group3.backend.Repositories;

import com.cs319group3.backend.Entities.UserEntities.Instructor;
import com.cs319group3.backend.Entities.UserEntities.TA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstructorRepo extends JpaRepository<Instructor, Integer> {
    Optional<Instructor> findByUserId(int userId);
    Optional<Instructor> findByName(String name);
    @Query("""
SELECT instructor.department.departmentId
FROM Instructor instructor
WHERE instructor.userId=:instructorId
""")
    int getDepartmentId(int instructorId);

}
