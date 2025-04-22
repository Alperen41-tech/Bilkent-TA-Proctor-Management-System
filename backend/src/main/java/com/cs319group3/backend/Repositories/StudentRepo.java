package com.cs319group3.backend.Repositories;

import com.cs319group3.backend.Entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepo extends JpaRepository<Student, Integer> {
    Optional<Student> findByBilkentId(String bilkentId);
}