package com.cs319group3.backend.Services.ServiceImpls;

import com.cs319group3.backend.Entities.Login;
import com.cs319group3.backend.Repositories.LoginRepo;
import com.cs319group3.backend.Services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    protected LoginRepo loginDAO;


    public boolean authenticate(String email, String password) {
        // Find the login by email
        Optional<Login> loginOptional = loginDAO.findByUserEmail(email);

        if (!loginOptional.isPresent()) {
            // If no login found, return false
            return false;
        }

        Login login = loginOptional.get();  // Get the Login entity

        // Check if the stored password matches the provided password
        if (!login.getPassword().equals(password)) {
            // If passwords do not match, return false
            return false;
        }

        // Check if the user is active
        if (!login.getUser().isActive()) {
            // If the user is not active, return false
            return false;
        }

        // If password matches and user is active, authentication is successful
        return true;
    }
}