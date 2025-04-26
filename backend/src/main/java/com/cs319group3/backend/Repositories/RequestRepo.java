package com.cs319group3.backend.Repositories;

import com.cs319group3.backend.Entities.RequestEntities.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RequestRepo extends JpaRepository<Request,String> {
    Optional<Request> findByRequestId(int requestId);
}
