package com.cs319group3.backend.Controllers.UserControllers;

import com.cs319group3.backend.Components.CurrentUserUtil;
import com.cs319group3.backend.DTOs.CreateDeansOfficeDTO;
import com.cs319group3.backend.DTOs.DeansOfficeProfileDTO;
import com.cs319group3.backend.Services.DeansOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

/**
 * Controller responsible for operations related to Dean's Office users,
 * including profile retrieval and account creation.
 */
@RestController
@RequestMapping("deansOffice")
@ComponentScan(basePackages = {"com.cs319group3.backend.Controllers.UserControllers"})
@CrossOrigin(origins = "http://localhost:3000")
public class DeansOfficeController {

    @Autowired
    private DeansOfficeService deansOfficeService;

    @Autowired
    private CurrentUserUtil currentUserUtil;

    /**
     * Retrieves the profile of the currently logged-in Dean's Office user.
     *
     * @return the profile DTO of the Dean's Office user
     */
    @GetMapping("profile")
    public DeansOfficeProfileDTO getProfile() {
        System.out.println("request received");
        int id = currentUserUtil.getCurrentUserId();
        return deansOfficeService.getDeansOfficeProfileById(id);
    }

    /**
     * Creates a new Dean's Office user account.
     *
     * @param createDeansOfficeDTO the account creation request payload
     * @return true if creation was successful
     */
    @PostMapping("createDeansOffice")
    public boolean createDeansOffice(@RequestBody CreateDeansOfficeDTO createDeansOfficeDTO) {
        System.out.println("create deans office");
        return deansOfficeService.createDeansOffice(createDeansOfficeDTO);
    }
}