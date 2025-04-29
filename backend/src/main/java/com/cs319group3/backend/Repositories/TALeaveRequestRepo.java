package com.cs319group3.backend.Repositories;

import com.cs319group3.backend.Entities.RequestEntities.TALeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TALeaveRequestRepo extends JpaRepository<TALeaveRequest, Integer> {

    List<TALeaveRequest> findByReceiverUser_UserId(int userId);

}
