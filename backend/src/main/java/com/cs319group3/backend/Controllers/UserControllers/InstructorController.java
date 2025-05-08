package com.cs319group3.backend.Controllers.UserControllers;

import com.cs319group3.backend.Components.CurrentUserUtil;
import com.cs319group3.backend.DTOs.CreateInstructorDTO;
import com.cs319group3.backend.DTOs.InstructorProfileDTO;
import com.cs319group3.backend.Services.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsible for managing instructor-related operations such as
 * account creation, profile retrieval, and listing all instructors.
 */
@RestController
@RequestMapping("instructor")
@ComponentScan(basePackages = {"com.cs319group3.backend.Controllers.UserControllers"})
@CrossOrigin(origins = "http://localhost:3000")
public class InstructorController {

    @Autowired
    private InstructorService instructorServiceImpl;

    @Autowired
    private CurrentUserUtil currentUserUtil;

    /**
     * Retrieves the profile of the currently logged-in instructor.
     *
     * @return InstructorProfileDTO of the current instructor
     */
    @GetMapping("profile")
    public InstructorProfileDTO getInstructorProfile(){
        System.out.println("request received");
        int id = currentUserUtil.getCurrentUserId();
        return instructorServiceImpl.getInstructorProfileById(id);
    }

    /**
     * Creates a new instructor account using the provided data.
     *
     * @param createInstuctorDTO the DTO containing instructor creation details
     * @return true if the account was created successfully
     */
    @PostMapping("createInstructor")
    public boolean createInstructor(@RequestBody CreateInstructorDTO createInstuctorDTO){
        System.out.println("create instructor");
        return instructorServiceImpl.createInstructor(createInstuctorDTO);
    }

    /**
     * Retrieves a list of all instructors in the system.
     *
     * @return a list of InstructorProfileDTOs
     */
    @GetMapping("getAllInstructors")
    public List<InstructorProfileDTO> getAllInstructors(){
        return instructorServiceImpl.getAllInstructors();
    }
}