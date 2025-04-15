package com.cs319group3.backend.Controllers;

import com.cs319group3.backend.Services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private LoginService loginService;

    // Inner class to map the JSON request body
    public static class LoginRequest {
        private String email;
        private String password;

        // Getters and setters (required for JSON parsing)
        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    @PostMapping("/login")
    public boolean login(@RequestBody LoginRequest loginRequest) {
        System.out.println("Login request received for: " + loginRequest.getPassword());
        return loginService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
    }
}