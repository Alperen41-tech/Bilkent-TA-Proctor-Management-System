package com.cs319group3.backend.Repositories;

import com.cs319group3.backend.Entities.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface LoginRepository extends JpaRepository<Login, Integer> {

    // Find the login details by user (you can customize this based on your needs)
    Optional<Login> findByUserMail(String mail);
}
