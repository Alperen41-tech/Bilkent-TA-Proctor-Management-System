package com.cs319group3.backend.Services.ServiceImpls;

import com.cs319group3.backend.DTOs.ChangePasswordDTO;
import com.cs319group3.backend.Entities.Login;
import com.cs319group3.backend.Enums.LogType;
import com.cs319group3.backend.Repositories.LoginRepo;
import com.cs319group3.backend.Services.AuthenticationService;
import com.cs319group3.backend.Services.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService, UserDetailsService {

    @Autowired
    private LoginRepo loginRepo;

    @Autowired
    private LogService logService;

    @Override
    public boolean changePassword(ChangePasswordDTO changePasswordDTO) {
        Optional<Login> login = loginRepo.findByUser_EmailAndUserType_UserTypeName(
                changePasswordDTO.getEmail(),
                changePasswordDTO.getUserTypeName()
        );

        if (login.isEmpty()) {
            return false;
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (encoder.matches(changePasswordDTO.getOldPassword(), login.get().getPassword())) {
            login.get().setPassword(encoder.encode(changePasswordDTO.getNewPassword()));
            loginRepo.save(login.get());

            String logMessage = "User " + login.get().getUser().getUserId() + " changed password.";
            logService.createLog(logMessage, LogType.UPDATE);

            return true;
        } else {
            System.out.println("Old password does not match");
            return false;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String entrance) throws UsernameNotFoundException {
        String[] parts = entrance.split("::");
        String email = parts[0];
        String userTypeFromFrontend = parts[1];

        Optional<Login> login = loginRepo.findByUser_EmailAndUserType_UserTypeName(email, userTypeFromFrontend);

        if (login.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        Login currLogin = login.get();

        return new org.springframework.security.core.userdetails.User(
                entrance,
                currLogin.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(currLogin.getUserType().getUserTypeName()))
        );
    }
}