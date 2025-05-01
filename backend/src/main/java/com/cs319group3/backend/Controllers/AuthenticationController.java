package com.cs319group3.backend.Controllers;

import com.cs319group3.backend.Components.JwtUtil;
import com.cs319group3.backend.DTOs.LoginDTO;
import com.cs319group3.backend.Services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @PutMapping("changePassword")
    public boolean changePassword(@RequestBody LoginDTO newLoginDTO) {
        return authenticationService.changePassword(newLoginDTO);
    }

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginRequest) {

        String combinedUsername = loginRequest.getEmail() + "::" + loginRequest.getUserTypeName();

        try {
            // Authenticate the user with the combined username (email + userType)
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(combinedUsername, loginRequest.getPassword())
            );

            // Extract additional information from loginRequest (assuming they exist in the LoginDTO)
            String email = loginRequest.getEmail();
            String userType = loginRequest.getUserTypeName();

            // Generate the token using all required parameters
            String token = jwtUtil.generateToken(combinedUsername, email, userType);

            return ResponseEntity.ok(token);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}
