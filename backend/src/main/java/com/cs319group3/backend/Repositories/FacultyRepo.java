package com.cs319group3.backend.Repositories;

import com.cs319group3.backend.Entities.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FacultyRepo extends JpaRepository<Faculty, Integer> {
    Optional<Faculty> findByFacultyId(int id);
}
