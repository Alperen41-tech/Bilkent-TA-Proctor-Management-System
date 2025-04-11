package com.cs319group3.backend.Controllers;

import com.cs319group3.backend.Services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public boolean login(@RequestParam String mail, @RequestParam String password) {
        return loginService.authenticate(mail, password);
    }
}
