package com.cs319group3.backend.Repositories;


import com.cs319group3.backend.Entities.RequestEntities.TASwapRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TASwapRequestRepo extends JpaRepository<TASwapRequest, Integer> {


    List<TASwapRequest> findByReceiverUser_UserId(Integer receiverId);
    List<TASwapRequest> findBySenderUser_UserId(Integer senderId);

    Optional<TASwapRequest> findBySenderUser_UserIdAndReceiverUser_UserIdAndClassProctoring_ClassProctoringId(int senderId, int receiverId, int classProctoringId);

    Optional<TASwapRequest> findBySenderUser_UserIdAndClassProctoring_ClassProctoringId(int senderId, int classProctoringId);
}
