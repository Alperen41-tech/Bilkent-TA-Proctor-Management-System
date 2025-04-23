package com.cs319group3.backend.Controllers;

import com.cs319group3.backend.Services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@ComponentScan(basePackages = {"com.cs319group3.backend.Controllers"})
@CrossOrigin(origins = "http://localhost:3000")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("deneme")
    public ResponseEntity<String> deneme() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return new ResponseEntity<>(email, HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<String> getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            return ResponseEntity.ok("Authenticated as: " + auth.getName());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not authenticated");
        }
    }
}