package com.cs319group3.backend.Repositories;

import com.cs319group3.backend.Entities.Semester;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SemesterRepo extends JpaRepository<Semester, Integer> {
    Optional<Semester> findBySemesterId(int id);

    Optional<Semester> findByYearAndTerm(String year, int term);
}
