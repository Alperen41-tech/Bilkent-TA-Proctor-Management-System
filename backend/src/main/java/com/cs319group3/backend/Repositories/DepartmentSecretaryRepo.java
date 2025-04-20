package com.cs319group3.backend.Repositories;

import com.cs319group3.backend.DTOs.DepartmentSecretaryProfileDTO;
import com.cs319group3.backend.Entities.UserEntities.DepartmentSecretary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentSecretaryRepo extends JpaRepository<DepartmentSecretary, Integer> {
    Optional<DepartmentSecretary> findByUserId(int id);
}
