package com.cs319group3.backend.Controllers;

import com.cs319group3.backend.DTOs.DeansOfficeProfileDTO;
import com.cs319group3.backend.Entities.UserEntities.DeansOffice;
import com.cs319group3.backend.Services.DeansOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("DeansOffice")
@ComponentScan(basePackages = {"com.cs319group3.backend.Controllers"})
@CrossOrigin(origins = "http://localhost:3000")
public class DeansOfficeController {

    @Autowired
    private DeansOfficeService deansOfficeService;

    @GetMapping("Profile")
    public DeansOfficeProfileDTO getProfile(@RequestParam("id") int id) {
        System.out.println("request received");
        return deansOfficeService.getDeansOfficeProfileById(id);
    }
}
