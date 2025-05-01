package com.cs319group3.backend.Services;

import com.cs319group3.backend.DTOs.LoginDTO;

public interface AuthenticationService {
    boolean changePassword(LoginDTO newLoginDTO);
}
