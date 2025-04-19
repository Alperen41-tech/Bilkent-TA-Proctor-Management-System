package com.cs319group3.backend.Controllers;

import com.cs319group3.backend.DTOs.InstructorProfileDTO;
import com.cs319group3.backend.DTOs.TAProfileDTO;
import com.cs319group3.backend.Services.InstructorProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("instructor")
@ComponentScan(basePackages = {"com.cs319group3.backend.Controllers"})
@CrossOrigin(origins = "http://localhost:3000")
public class InstructorContoller {

    @Autowired
    private InstructorProfileService instructorProfileServiceImpl;

    @GetMapping("profile")
    public InstructorProfileDTO getInstructorProfile(@RequestParam("id") int id){
        System.out.println("request received");
        return instructorProfileServiceImpl.getInstructorProfileById(id);
    }

}
