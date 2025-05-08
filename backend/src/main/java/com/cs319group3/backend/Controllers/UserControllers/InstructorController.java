package com.cs319group3.backend.Controllers.UserControllers;

import com.cs319group3.backend.Components.CurrentUserUtil;
import com.cs319group3.backend.DTOs.CreateInstructorDTO;
import com.cs319group3.backend.DTOs.InstructorProfileDTO;
import com.cs319group3.backend.Services.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("instructor")
@ComponentScan(basePackages = {"com.cs319group3.backend.Controllers.UserControllers"})
@CrossOrigin(origins = "http://localhost:3000")
public class InstructorController {

    @Autowired
    private InstructorService instructorServiceImpl;
    @Autowired
    private CurrentUserUtil currentUserUtil;

    @GetMapping("profile")
    public InstructorProfileDTO getInstructorProfile(){
        System.out.println("request received");
        int id = currentUserUtil.getCurrentUserId();
        return instructorServiceImpl.getInstructorProfileById(id);
    }

    @PostMapping("createInstructor")
    public boolean createInstructor(@RequestBody CreateInstructorDTO createInstuctorDTO){
        System.out.println("create instructor");
        return instructorServiceImpl.createInstructor(createInstuctorDTO);
    }

    @GetMapping("getAllInstructors")
    public List<InstructorProfileDTO> getAllInstructors(){
        return instructorServiceImpl.getAllInstructors();
    }


}
