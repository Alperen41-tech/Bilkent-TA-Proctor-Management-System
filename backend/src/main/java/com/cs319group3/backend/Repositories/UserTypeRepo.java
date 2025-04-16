package com.cs319group3.backend.Repositories;


import com.cs319group3.backend.Entities.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTypeRepo extends JpaRepository<UserType, Integer> {






}
