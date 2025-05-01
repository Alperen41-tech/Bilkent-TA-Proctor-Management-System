package com.cs319group3.backend.Services.ServiceImpls;

import com.cs319group3.backend.DTOs.LoginDTO;
import com.cs319group3.backend.Entities.Login;
import com.cs319group3.backend.Repositories.LoginRepo;
import com.cs319group3.backend.Services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private LoginRepo loginRepo;

    @Override
    public boolean changePassword(LoginDTO loginNewDTO) {
        Optional<Login> login = loginRepo.findByUser_EmailAndUserType_UserTypeName(loginNewDTO.getEmail(), loginNewDTO.getUserTypeName());
        if (!login.isPresent()) {
            return false;
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        login.get().setPassword(encoder.encode(loginNewDTO.getPassword()));
        loginRepo.save(login.get());
        return true;
    }
}
