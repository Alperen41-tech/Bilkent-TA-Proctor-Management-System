package com.cs319group3.backend.Controllers.UserControllers;

import com.cs319group3.backend.DTOs.CreateInstructorDTO;
import com.cs319group3.backend.DTOs.InstructorProfileDTO;
import com.cs319group3.backend.DTOs.TaskTypeDTO;
import com.cs319group3.backend.Services.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("instructor")
@ComponentScan(basePackages = {"com.cs319group3.backend.Controllers.UserControllers"})
@CrossOrigin(origins = "http://localhost:3000")
public class InstructorContoller {

    @Autowired
    private InstructorService instructorServiceImpl;

    @GetMapping("profile")
    public InstructorProfileDTO getInstructorProfile(@RequestParam("id") int id){
        System.out.println("request received");
        return instructorServiceImpl.getInstructorProfileById(id);
    }

    @PostMapping("createInstructor")
    public boolean createInstructor(@RequestBody CreateInstructorDTO createInstuctorDTO){
        System.out.println("create instructor");
        return instructorServiceImpl.createInstructor(createInstuctorDTO);
    }
}
