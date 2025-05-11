package com.cs319group3.backend.Repositories;

import com.cs319group3.backend.Entities.RequestEntities.AuthStaffProctoringRequest;
import com.cs319group3.backend.Entities.RequestEntities.InstructorAdditionalTARequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InstructorAdditionalTARequestRepo extends JpaRepository<InstructorAdditionalTARequest, Integer> {
    Optional<InstructorAdditionalTARequest> findByRequestId(int requestId);
    List<InstructorAdditionalTARequest> findByReceiverUser_UserId(int receiverId);
    List<InstructorAdditionalTARequest> findBySenderUser_UserId(int receiverId);

    @Query("""
SELECT r FROM InstructorAdditionalTARequest r 
WHERE r.receiverUser.userId = :receiverId 
AND r.approved = false
AND r.isSentToSecretary = false
AND r.responseDate IS NULL
""")
    List<InstructorAdditionalTARequest> findByReceiverIdAndIsApprovedFalseAndResponseDateNull(int receiverId);
    @Query("""
SELECT r FROM InstructorAdditionalTARequest r 
WHERE r.receiverUser.userId = :receiverId 
AND r.approved = true
AND r.isSentToSecretary = false
""")
    List<InstructorAdditionalTARequest> findByReceiverIdAndIsApprovedTrue(int receiverId);

    List<InstructorAdditionalTARequest> findByClassProctoring_ClassProctoringId(int classProctoringId);
}
