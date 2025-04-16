package com.cs319group3.backend.Repositories;

import com.cs319group3.backend.Entities.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LoginRepo extends JpaRepository<Login, Integer> {

    //Find the login details by user (you can customize this based on your needs)
    Optional<Login> findByUserEmail(String email);

    @Query("SELECT l FROM Login l WHERE l.user.userId = (SELECT u.userId FROM User u WHERE u.email = :email)")
    Optional<Login> findCustom(@Param("email") String email);
}
