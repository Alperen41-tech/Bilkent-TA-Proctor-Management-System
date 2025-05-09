package com.cs319group3.backend.Controllers;

import com.cs319group3.backend.Components.CurrentUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for testing and retrieving information about the currently logged-in user.
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class LoginController {

    @Autowired
    private CurrentUserUtil currentUserUtil;

    /**
     * Returns the authenticated user's email using Spring Security's context.
     *
     * @return the email of the currently authenticated user
     */
    @GetMapping("deneme")
    public ResponseEntity<String> deneme() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return new ResponseEntity<>(email, HttpStatus.OK);
    }

    /**
     * Returns details of the currently logged-in user based on custom CurrentUserUtil.
     *
     * @return a string containing email, ID, user type ID, and role name
     */
    @GetMapping("/me")
    public ResponseEntity<String> getCurrentUser() {
        String currentUserEmail = currentUserUtil.getCurrentEmail();
        int id = currentUserUtil.getCurrentUserId();
        int userTypeId = currentUserUtil.getCurrentUserTypeId();
        String currentUsername = currentUserUtil.getCurrentUserTypeName();

        String res = "email: " + currentUserEmail +
                " id: " + id +
                " type: " + userTypeId +
                " usermail: " + currentUserEmail +
                " name: " + currentUsername;
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}