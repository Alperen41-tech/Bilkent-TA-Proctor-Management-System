package com.cs319group3.backend.Repositories;

import com.cs319group3.backend.Entities.UserEntities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    Optional<User> findByBilkentId(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByUserId(int userId);
}