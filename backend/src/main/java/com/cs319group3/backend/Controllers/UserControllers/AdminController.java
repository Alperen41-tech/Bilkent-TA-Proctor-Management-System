package com.cs319group3.backend.Controllers.UserControllers;

import com.cs319group3.backend.Components.CurrentUserUtil;
import com.cs319group3.backend.DTOs.AdminProfileDTO;
import com.cs319group3.backend.Services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {
    @Autowired
    AdminService adminService;
    @Autowired
    private CurrentUserUtil currentUserUtil;

    @GetMapping("getAdminProfile")
    public AdminProfileDTO getAdminProfile() {
        int adminId = currentUserUtil.getCurrentUserId();
        return adminService.getAdminProfile(adminId);
    }

    @PostMapping("createAdmin")
    public boolean createAdmin(@RequestParam int userId, @RequestParam String password) {
        System.out.println("creating admin");
        return adminService.createAdmin(userId, password);
    }
}
