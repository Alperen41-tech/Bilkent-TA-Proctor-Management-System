package com.cs319group3.backend.Repositories;

import com.cs319group3.backend.Entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepo extends JpaRepository<Department, Integer> {
    Optional<Department> findByDepartmentName(String name);
}
