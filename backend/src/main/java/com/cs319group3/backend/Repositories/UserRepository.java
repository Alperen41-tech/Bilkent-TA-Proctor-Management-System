package com.cs319group3.backend.Repositories;

import com.cs319group3.backend.Entities.UserEntities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

}