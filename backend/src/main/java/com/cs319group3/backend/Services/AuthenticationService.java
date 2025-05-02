package com.cs319group3.backend.Services;

import com.cs319group3.backend.DTOs.ChangePasswordDTO;
import com.cs319group3.backend.DTOs.LoginDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface AuthenticationService {
    boolean changePassword(ChangePasswordDTO newLoginDTO);
    UserDetails loadUserByUsername(String entrance) throws UsernameNotFoundException;
}
