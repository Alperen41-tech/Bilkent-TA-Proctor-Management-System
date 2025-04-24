package com.cs319group3.backend.Repositories;

import com.cs319group3.backend.Entities.TaskType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskTypeRepo extends JpaRepository<TaskType, Integer> {
    Optional<TaskType> findByTaskTypeName(String taskTypeName);
}
