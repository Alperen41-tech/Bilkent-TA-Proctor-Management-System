package com.cs319group3.backend.Repositories;

import com.cs319group3.backend.Entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepo extends JpaRepository<Student, Integer> {
    Optional<Student> findByBilkentId(String bilkentId);

    @Query(value = """
    SELECT s.class FROM student s
    JOIN user u ON s.bilkent_id = u.bilkent_id
    WHERE u.user_id = :userId
    """, nativeQuery = true)
    Optional<Integer> findClassById(int userId);
}