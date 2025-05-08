package com.cs319group3.backend.Controllers.UserControllers;

import com.cs319group3.backend.Components.CurrentUserUtil;
import com.cs319group3.backend.DTOs.CreateDepartmentSecretaryDTO;
import com.cs319group3.backend.DTOs.DepartmentSecretaryProfileDTO;
import com.cs319group3.backend.Services.DepartmentSecretaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

/**
 * Controller responsible for managing Department Secretary accounts,
 * including profile retrieval and account creation.
 */
@RestController
@RequestMapping("departmentSecretary")
@ComponentScan(basePackages = {"com.cs319group3.backend.Controllers.UserControllers"})
@CrossOrigin(origins = "http://localhost:3000")
public class DepartmentSecretaryController {

    @Autowired
    private DepartmentSecretaryService departmentSecretaryService;

    @Autowired
    private CurrentUserUtil currentUserUtil;

    /**
     * Retrieves the profile of the currently logged-in department secretary.
     *
     * @return the profile DTO of the department secretary
     */
    @GetMapping("profile")
    public DepartmentSecretaryProfileDTO getProfile() {
        System.out.println("request received");
        int id = currentUserUtil.getCurrentUserId();
        return departmentSecretaryService.getDepartmentSecretaryProfileById(id);
    }

    /**
     * Creates a new department secretary account.
     *
     * @param cdsDTO the account creation data
     * @return true if the account was created successfully
     */
    @PostMapping("createDepartmentSecretary")
    public boolean createDepartmentSecretary(@RequestBody CreateDepartmentSecretaryDTO cdsDTO) {
        System.out.println("create department secretary");
        return departmentSecretaryService.createDepartmentSecretary(cdsDTO);
    }
}