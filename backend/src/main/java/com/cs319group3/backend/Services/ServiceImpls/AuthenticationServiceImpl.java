package com.cs319group3.backend.Services.ServiceImpls;

import com.cs319group3.backend.Entities.Login;
import com.cs319group3.backend.Repositories.LoginRepo;
import com.cs319group3.backend.Services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private LoginRepo loginRepo;

    @Override
    public boolean changePassword(int id, String newPassword) {
        Optional<Login> login = loginRepo.findByUser_UserId(id);
        if (login.isEmpty()) {
            return false;
        }
        login.get().setPassword(newPassword);
        loginRepo.save(login.get());
        return true;
    }
}
