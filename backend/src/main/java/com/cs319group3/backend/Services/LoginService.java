package com.cs319group3.backend.Services;

import com.cs319group3.backend.Entities.Login;
import com.cs319group3.backend.Entities.UserEntities.User;
import com.cs319group3.backend.Repositories.LoginRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private LoginRepository loginRepository;

    public boolean authenticate(String email, String password) {
        Optional<Login> loginOptional = loginRepository.findByUserEmail(email);

        if (!loginOptional.isPresent()) {
            return false;
        }

        Login login = loginOptional.get();

        if (!login.getPassword().equals(password)) {
            return false;
        }

        if (!login.getUser().isActive()) {
            return false;
        }

        return true;
    }
}
