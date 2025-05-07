package com.cs319group3.backend.Repositories;

import com.cs319group3.backend.Entities.TAType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TATypeRepo extends JpaRepository<TAType, Integer> {


    Optional<TAType> findByTypeName(String typeName);

}
