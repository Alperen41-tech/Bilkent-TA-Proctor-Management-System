package com.cs319group3.backend.Controllers;

import com.cs319group3.backend.Components.JwtUtil;
import com.cs319group3.backend.DTOs.LoginDTO;
import com.cs319group3.backend.Services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PutMapping("ChangePassword")
    public boolean changePassword(@RequestParam int id, @RequestParam String newPassword) {
        System.out.println("Changing password for " + id + " to " + newPassword);
        return authenticationService.changePassword(id, newPassword);
    }

    @PostMapping("login")
    public ResponseEntity<Boolean> login(@RequestBody LoginDTO loginRequest) {

        System.out.println("hello llellelelelel");
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );

            String token = jwtUtil.generateToken(loginRequest.getEmail());
            return ResponseEntity.ok(true);
        }
        catch (BadCredentialsException e) {
            return ResponseEntity.ok(false);
        }
    }



}
