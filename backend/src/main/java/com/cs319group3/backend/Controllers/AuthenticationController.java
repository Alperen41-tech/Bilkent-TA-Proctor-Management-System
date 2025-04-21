package com.cs319group3.backend.Controllers;

import com.cs319group3.backend.Services.AuthenticationService;
import com.cs319group3.backend.Services.DepartmentSecretaryService;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("Authentication")
@ComponentScan(basePackages = {"com.cs319group3.backend.Controllers"})
@CrossOrigin(origins = "http://localhost:3000")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PutMapping("ChangePassword")
    public boolean changePassword(@RequestParam int id, @RequestParam String newPassword) {
        System.out.println("Changing password for " + id + " to " + newPassword);
        return authenticationService.changePassword(id, newPassword);
    }
}
