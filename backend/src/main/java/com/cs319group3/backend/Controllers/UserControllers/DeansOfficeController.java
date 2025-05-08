package com.cs319group3.backend.Controllers.UserControllers;

import com.cs319group3.backend.Components.CurrentUserUtil;
import com.cs319group3.backend.DTOs.CreateDeansOfficeDTO;
import com.cs319group3.backend.DTOs.DeansOfficeProfileDTO;
import com.cs319group3.backend.Entities.UserEntities.DeansOffice;
import com.cs319group3.backend.Services.DeansOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("deansOffice")
@ComponentScan(basePackages = {"com.cs319group3.backend.Controllers.UserControllers"})
@CrossOrigin(origins = "http://localhost:3000")
public class DeansOfficeController {

    @Autowired
    private DeansOfficeService deansOfficeService;
    @Autowired
    private CurrentUserUtil currentUserUtil;

    @GetMapping("profile")
    public DeansOfficeProfileDTO getProfile() {
        System.out.println("request received");
        int id = currentUserUtil.getCurrentUserId();
        return deansOfficeService.getDeansOfficeProfileById(id);
    }

    @PostMapping("createDeansOffice")
    public boolean createDeansOffice(@RequestBody CreateDeansOfficeDTO createDeansOfficeDTO) {
        System.out.println("create deans office");
        return deansOfficeService.createDeansOffice(createDeansOfficeDTO);
    }
}
