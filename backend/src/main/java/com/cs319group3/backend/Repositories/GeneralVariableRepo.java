package com.cs319group3.backend.Repositories;

import com.cs319group3.backend.Entities.GeneralVariable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface GeneralVariableRepo extends JpaRepository<GeneralVariable, Integer> {
    @Query(value = "SELECT semester_id FROM general_variable LIMIT 1", nativeQuery = true)
    int getSemesterId();
}
