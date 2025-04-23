package com.cs319group3.backend.Services.ServiceImpls;

import com.cs319group3.backend.Entities.Login;
import com.cs319group3.backend.Repositories.LoginRepo;
import com.cs319group3.backend.Services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService, UserDetailsService {

    @Autowired
    protected LoginRepo loginDAO;

    @Override
    public UserDetails loadUserByUsername(String entrance) throws UsernameNotFoundException {

        String[] parts = entrance.split("::");
        String email = parts[0];
        String userTypeFromFrontend = parts[1];

        Login currLogin = loginDAO.findByUser_EmailAndUserType_UserTypeName(email, userTypeFromFrontend);




        System.out.println("hello world 2");

        return new org.springframework.security.core.userdetails.User(
                entrance,
                currLogin.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(currLogin.getUserType().getUserTypeName()))
        );
    }
}