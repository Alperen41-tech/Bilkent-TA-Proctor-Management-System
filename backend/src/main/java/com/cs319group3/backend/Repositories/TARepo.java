package com.cs319group3.backend.Repositories;


import com.cs319group3.backend.Entities.UserEntities.TA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TARepo extends JpaRepository<TA, Integer> {
    Optional<TA> findByUserId(int userId);
}
