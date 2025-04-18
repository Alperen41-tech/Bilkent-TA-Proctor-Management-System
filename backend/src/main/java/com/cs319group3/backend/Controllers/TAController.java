package com.cs319group3.backend.Controllers;



import com.cs319group3.backend.DTOs.TAProfileDTO;
import com.cs319group3.backend.Services.ServiceImpls.TAProfileServiceImpl;
import com.cs319group3.backend.Services.TAProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("ta")
@ComponentScan(basePackages = {"com.cs319group3.backend.Controllers"})
@CrossOrigin(origins = "http://localhost:3000")
public class TAController {

    @Autowired
    private TAProfileServiceImpl TAProfileServiceImplService;

    @GetMapping("profile")
    public TAProfileDTO getTAProfile(@RequestParam("id") int id){
        System.out.println("request received");
        return TAProfileServiceImplService.getTAProfileById(id);
    }

}
