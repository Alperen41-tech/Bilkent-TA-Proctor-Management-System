package com.cs319group3.backend.Repositories;

import com.cs319group3.backend.Entities.UserEntities.DeansOffice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeansOfficeRepo extends JpaRepository<DeansOffice, Integer> {
    Optional<DeansOffice> findByUserId(int userId);
}
