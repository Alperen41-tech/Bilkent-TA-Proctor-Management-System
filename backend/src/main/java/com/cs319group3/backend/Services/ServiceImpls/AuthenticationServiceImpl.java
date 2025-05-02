package com.cs319group3.backend.Services.ServiceImpls;

import com.cs319group3.backend.DTOs.ChangePasswordDTO;
import com.cs319group3.backend.DTOs.LoginDTO;
import com.cs319group3.backend.Entities.Login;
import com.cs319group3.backend.Repositories.LoginRepo;
import com.cs319group3.backend.Services.AuthenticationService;
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

    @Override
    public boolean changePassword(ChangePasswordDTO changePasswordDTO) {
        Optional<Login> login = loginRepo.findByUser_EmailAndUserType_UserTypeName(changePasswordDTO.getEmail(), changePasswordDTO.getUserTypeName());
        if (!login.isPresent()) {
            return false;
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String oldPassword = encoder.encode(changePasswordDTO.getOldPassword());

        if (encoder.matches(changePasswordDTO.getOldPassword(), login.get().getPassword())) {
            // Password matches, update to new password
            login.get().setPassword(encoder.encode(changePasswordDTO.getNewPassword()));
            loginRepo.save(login.get());
            return true;
        } else {
            System.out.println("Old password does not match");
            return false;
        }
    }


    @Autowired
    protected LoginRepo loginDAO;

    @Override
    public UserDetails loadUserByUsername(String entrance) throws UsernameNotFoundException {

        String[] parts = entrance.split("::");
        String email = parts[0];
        String userTypeFromFrontend = parts[1];

        Optional<Login> login = loginDAO.findByUser_EmailAndUserType_UserTypeName(email, userTypeFromFrontend);


        if (!login.isPresent()) {
            throw new UsernameNotFoundException("User not found");
        }

        Login currLogin = login.get();

        System.out.println("hello world 2");

        return new org.springframework.security.core.userdetails.User(
                entrance,
                currLogin.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(currLogin.getUserType().getUserTypeName()))
        );
    }
}
