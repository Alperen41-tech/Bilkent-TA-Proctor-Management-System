package com.cs319group3.backend.Controllers.RequestControllers;


import com.cs319group3.backend.DTOs.RequestDTOs.RequestDTO;
import com.cs319group3.backend.Services.InstructorAdditionalTARequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("taFromDeanRequest")
@ComponentScan(basePackages = {"com.cs319group3.backend.Controllers.RequestControllers"})
@CrossOrigin(origins = "http://localhost:3000")
public class InstructorAdditionalTARequestController {
    @Autowired
    InstructorAdditionalTARequestService instructorAdditionalTARequestService;
    //Fetches all the instructor additional ta request from database using the give receiver id
    @GetMapping("getInstructorAdditionalTARequests")
    public List<RequestDTO> getInstructorAdditionalTARequests(@RequestParam int receiverId) {
        System.out.println("Fetching Instructor Additional TA Requests for dean");
        return instructorAdditionalTARequestService.getInstructorAdditionalTARequests(receiverId);
    }

}
