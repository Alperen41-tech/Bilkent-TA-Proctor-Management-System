package com.cs319group3.backend.Controllers;

import com.cs319group3.backend.DTOs.LoginDTO;
import com.cs319group3.backend.Services.LoginService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@ComponentScan(basePackages = {"com.cs319group3.backend.Controllers"})
@CrossOrigin(origins = "http://localhost:3000")
public class LoginController {

    @Autowired
    private LoginService loginService;


    @PostMapping("/login")
    public ResponseEntity<Boolean> login(@RequestBody LoginDTO loginRequest) {
        try {
            System.out.println("Login request received for: " + loginRequest.getEmail());
            boolean success = loginService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            System.err.println("Login failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }
}