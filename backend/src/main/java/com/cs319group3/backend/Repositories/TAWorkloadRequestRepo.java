package com.cs319group3.backend.Repositories;

import com.cs319group3.backend.Entities.RequestEntities.TAWorkloadRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TAWorkloadRequestRepo extends JpaRepository<TAWorkloadRequest, Integer> {
    List<TAWorkloadRequest> findBySenderUser_UserId(int senderUserUserId);
    List<TAWorkloadRequest> findByReceiverUser_UserId(int receiverUserUserId);
    List<TAWorkloadRequest> findByWorkloadId(int workloadId);

    @Query("""
    SELECT MAX(workloadId) AS workloadId FROM TAWorkloadRequest
""")
    Integer getMaxWorkloadId();
}
