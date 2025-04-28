package com.cs319group3.backend.Repositories;


import com.cs319group3.backend.Entities.RequestEntities.TASwapRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TASwapRequestRepo extends JpaRepository<TASwapRequest, Integer> {


    List<TASwapRequest> findByReceiverUser_UserId(Integer receiverId);
    List<TASwapRequest> findBySenderUser_UserId(Integer senderId);
}
