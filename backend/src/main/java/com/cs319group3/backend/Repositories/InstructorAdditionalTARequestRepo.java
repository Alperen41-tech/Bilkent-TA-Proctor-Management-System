package com.cs319group3.backend.Repositories;

import com.cs319group3.backend.Entities.RequestEntities.InstructorAdditionalTARequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstructorAdditionalTARequestRepo extends JpaRepository<InstructorAdditionalTARequest, Integer> {
    List<InstructorAdditionalTARequest> findByReceiverUser_UserId(int receiverId);
    List<InstructorAdditionalTARequest> findBySenderUser_UserId(int receiverId);

}
