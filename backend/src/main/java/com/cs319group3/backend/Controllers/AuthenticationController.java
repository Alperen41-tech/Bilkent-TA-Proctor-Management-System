package com.cs319group3.backend.Controllers;

import com.cs319group3.backend.Components.CurrentUserUtil;
import com.cs319group3.backend.Components.JwtUtil;
import com.cs319group3.backend.DTOs.ChangePasswordDTO;
import com.cs319group3.backend.DTOs.LoginDTO;
import com.cs319group3.backend.DTOs.TokenDTO;
import com.cs319group3.backend.Entities.Log;
import com.cs319group3.backend.Entities.UserEntities.*;
import com.cs319group3.backend.Entities.UserType;
import com.cs319group3.backend.Enums.LogType;
import com.cs319group3.backend.Repositories.LogRepo;
import com.cs319group3.backend.Repositories.UserRepo;
import com.cs319group3.backend.Repositories.UserTypeRepo;
import com.cs319group3.backend.Services.AuthenticationService;
import com.cs319group3.backend.Services.LogService;
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

/**
 * Controller responsible for authentication-related operations,
 * such as logging in and changing passwords.
 */
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
    @Autowired
    private CurrentUserUtil currentUserUtil;
    @Autowired
    private LogService logService;
    private LogRepo logRepo;

    /**
     * Allows the current user to change their password.
     *
     * @param changePasswordDTO contains old and new password information
     * @return true if the password was changed successfully, false otherwise
     */
    @PutMapping("changePassword")
    public boolean changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
        return authenticationService.changePassword(changePasswordDTO);
    }

    /**
     * An endpoint in order to reset the user's password
     * @param userMail
     * @param userTypeName
     * @return
     */
    @GetMapping("forgetPassword")
    public boolean forgetPassword(@RequestParam("userMail") String userMail, @RequestParam("userTypeName") String userTypeName){
        return authenticationService.forgetPassword(userMail, userTypeName);
    }

    @GetMapping("setAfterForgetPassword")
    public boolean setAfterForgetPassword(@RequestParam("token") String token, @RequestParam("newPassword") String newPassword){
        return authenticationService.setAfterForgetPassword(token, newPassword);
    }

    /**
     * Authenticates the user with email, password, and user type, then generates a JWT.
     *
     * @param loginRequest the user's login credentials and requested role
     * @return a JWT token and user type upon successful login; otherwise, an error response
     */
    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO loginRequest) {
        try {
            Optional<User> userReceived = userRepo.findByEmail(loginRequest.getEmail());

            if (userReceived.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }

            User user = userReceived.get();
            int userTypeId = 0;
            String userTypeName = "";

            if (loginRequest.getUserTypeName() == null) {
                if (user instanceof DepartmentSecretary) {
                    userTypeName = "department secretary";
                } else if (user instanceof TA) {
                    userTypeName = "ta";
                } else if (user instanceof Instructor) {
                    userTypeName = "instructor";
                } else if (user instanceof DeansOffice) {
                    userTypeName = "deans office";
                }
            } else {
                userTypeName = "admin";
            }

            String combinedUsername = loginRequest.getEmail() + "::" + userTypeName;

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(combinedUsername, loginRequest.getPassword())
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            UserType userTypeOptional = userTypeRepo.findByUserTypeName(userTypeName);
            userTypeId = userTypeOptional.getUserTypeId();

            String token = jwtUtil.generateToken(
                    combinedUsername,
                    loginRequest.getEmail(),
                    user.getUserId(),
                    userTypeId
            );

            String logMessage = "User " + user.getUserId() + " logged in.";
            logService.createLog(logMessage, LogType.LOGIN);
            return ResponseEntity.ok(new TokenDTO(token, userTypeName));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("logout")
    public void logout() {
        int userId = currentUserUtil.getCurrentUserId();
        String logMessage = "User " + userId + " logged out.";
        logService.createLog(logMessage, LogType.LOGIN);
    }
}