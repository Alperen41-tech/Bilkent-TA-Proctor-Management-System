package com.cs319group3.backend.Controllers;

import com.cs319group3.backend.DTOs.DepartmentSecretaryProfileDTO;
import com.cs319group3.backend.Services.DepartmentSecretaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("DepartmentSecretary")
@ComponentScan(basePackages = {"com.cs319group3.backend.Controllers"})
@CrossOrigin(origins = "http://localhost:3000")
public class DepartmentSecretaryController {

    @Autowired
    private DepartmentSecretaryService departmentSecretaryService;

    @GetMapping("Profile")
    public DepartmentSecretaryProfileDTO getProfile(@RequestParam("id") int id) {
        System.out.println("request received");
        return departmentSecretaryService.getDepartmentSecretaryProfileById(id);
    }
}
