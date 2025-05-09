package com.cs319group3.backend.Repositories;
import org.springframework.data.jpa.repository.Query;
import com.cs319group3.backend.Entities.RequestEntities.AuthStaffProctoringRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthStaffProctoringRequestRepo extends JpaRepository<AuthStaffProctoringRequest, Integer> {

    List<AuthStaffProctoringRequest> findByReceiverUser_UserId(int userId);
    List<AuthStaffProctoringRequest> findBySenderUser_UserId(int userId);
    List<AuthStaffProctoringRequest> findBySenderUserUserIdAndReceiverUserUserIdAndClassProctoringClassProctoringIdAndApprovedFalseAndResponseDateIsNull(int senderId, int taId, int classProctoringId);
    List<AuthStaffProctoringRequest> findByReceiverUserUserIdAndClassProctoringClassProctoringIdAndApprovedFalseAndResponseDateIsNull(int taId, int classProctoringId);

    List<AuthStaffProctoringRequest> findByClassProctoringClassProctoringIdAndResponseDateIsNullAndApprovedFalse(
            int classProctoringId
    );

    @Query("""
    SELECT COUNT(asp)
    FROM AuthStaffProctoringRequest asp
    JOIN Request r ON asp.requestId = r.requestId
    WHERE asp.classProctoring.classProctoringId = :classProctoringId AND r.approved = false AND r.responseDate = null
""")
    int numberOfRequestsSent(int classProctoringId);
}
