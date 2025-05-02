package com.cs319group3.backend.Controllers.UserControllers;

import com.cs319group3.backend.DTOs.CreateDepartmentSecretaryDTO;
import com.cs319group3.backend.DTOs.DepartmentSecretaryProfileDTO;
import com.cs319group3.backend.Services.DepartmentSecretaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("departmentSecretary")
@ComponentScan(basePackages = {"com.cs319group3.backend.Controllers.UserControllers"})
@CrossOrigin(origins = "http://localhost:3000")
public class DepartmentSecretaryController {

    @Autowired
    private DepartmentSecretaryService departmentSecretaryService;

    @GetMapping("profile")
    public DepartmentSecretaryProfileDTO getProfile(@RequestParam("id") int id) {
        System.out.println("request received");
        return departmentSecretaryService.getDepartmentSecretaryProfileById(id);
    }

    @PostMapping("createDepartmentSecretary")
    public boolean createDepartmentSecretary(@RequestBody CreateDepartmentSecretaryDTO cdsDTO) {
        System.out.println("create department secretary");
        return departmentSecretaryService.createDepartmentSecretary(cdsDTO);
    }
}
