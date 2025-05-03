package com.cs319group3.backend.Repositories;

import com.cs319group3.backend.Entities.RequestEntities.AuthStaffProctoringRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthStaffProctoringRequestRepo extends JpaRepository<AuthStaffProctoringRequest, Integer> {

    List<AuthStaffProctoringRequest> findByReceiverUser_UserId(int userId);
    List<AuthStaffProctoringRequest> findBySenderUser_UserId(int userId);
    Optional<AuthStaffProctoringRequest> findBySenderUserUserIdAndReceiverUserUserIdAndClassProctoringClassProctoringId(int senderId, int taId, int classProctoringId);

}
