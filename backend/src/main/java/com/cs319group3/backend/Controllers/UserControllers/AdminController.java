package com.cs319group3.backend.Controllers.UserControllers;

import com.cs319group3.backend.Components.CurrentUserUtil;
import com.cs319group3.backend.DTOs.AdminProfileDTO;
import com.cs319group3.backend.Services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Controller responsible for handling operations related to admin users,
 * such as profile retrieval and account creation.
 */
@RestController
@RequestMapping("admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private CurrentUserUtil currentUserUtil;

    /**
     * Retrieves the profile of the currently logged-in admin user.
     *
     * @return the AdminProfileDTO for the current user
     */
    @GetMapping("getAdminProfile")
    public AdminProfileDTO getAdminProfile() {
        int adminId = currentUserUtil.getCurrentUserId();
        return adminService.getAdminProfile(adminId);
    }

    /**
     * Creates a new admin user with the specified user ID and password.
     *
     * @param userId the user ID to promote to admin
     * @param password the password to assign to the admin
     * @return true if the admin was created successfully
     */
    @PostMapping("createAdmin")
    public boolean createAdmin(@RequestParam int userId, @RequestParam String password) {
        System.out.println("creating admin");
        return adminService.createAdmin(userId, password);
    }
}