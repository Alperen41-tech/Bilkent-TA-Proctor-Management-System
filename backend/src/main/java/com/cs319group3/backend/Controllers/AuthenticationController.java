package com.cs319group3.backend.Controllers;

import com.cs319group3.backend.Components.JwtUtil;
import com.cs319group3.backend.DTOs.ChangePasswordDTO;
import com.cs319group3.backend.DTOs.LoginDTO;
import com.cs319group3.backend.Entities.Login;
import com.cs319group3.backend.Entities.UserEntities.User;
import com.cs319group3.backend.Entities.UserType;
import com.cs319group3.backend.Repositories.UserRepo;
import com.cs319group3.backend.Repositories.UserTypeRepo;
import com.cs319group3.backend.Services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserTypeRepo userTypeRepo;

    @PutMapping("changePassword")
    public boolean changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
        return authenticationService.changePassword(changePasswordDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginRequest) {
        try {
            // Create a combined username format for authentication
            String combinedUsername = loginRequest.getEmail() + "::" + loginRequest.getUserTypeName();

            // Authenticate with Spring Security
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(combinedUsername, loginRequest.getPassword())
            );

            // Get authenticated user details
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // Fetch additional user data from service
            Optional<User> userReceived = userRepo.findByEmail(loginRequest.getEmail());

            if (userReceived.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("User not found with the provided email and user type");
            }

            User user = userReceived.get();
            int userTypeId = 0;
            for (Login userType : user.getLogins()) {
                if (userType.getUserType().getUserTypeName().equals(loginRequest.getUserTypeName())){
                    userTypeId = userType.getUserType().getUserTypeId();
                    break;
                }
            }



            // Generate JWT token with user information
            String token = jwtUtil.generateToken(
                    combinedUsername,
                    loginRequest.getEmail(),
                    user.getUserId(),
                    userTypeId
            );

            return ResponseEntity.ok(token);

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid email or password");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Authentication error: " + e.getMessage());
        }
    }
}
