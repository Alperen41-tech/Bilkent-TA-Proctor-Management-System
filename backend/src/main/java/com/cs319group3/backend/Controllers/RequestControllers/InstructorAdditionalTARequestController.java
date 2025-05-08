package com.cs319group3.backend.Controllers.RequestControllers;


import com.cs319group3.backend.Components.CurrentUserUtil;
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
    private InstructorAdditionalTARequestService instructorAdditionalTARequestService;
    @Autowired
    private CurrentUserUtil currentUserUtil;

    //Fetches all approved instructor additional ta request from database using the give receiver id
    @GetMapping("getApprovedInstructorAdditionalTARequests")
    public List<RequestDTO> getApprovedInstructorAdditionalTARequests() {
        int receiverId = currentUserUtil.getCurrentUserId();
        System.out.println("Fetching Instructor Additional TA Requests for dean");
        return instructorAdditionalTARequestService.getApprovedInstructorAdditionalTARequests(receiverId);
    }
    //Fetches all approved instructor additional ta request from database using the give receiver id
    @GetMapping("getUnapprovedInstructorAdditionalTARequests")
    public List<RequestDTO> getUnapprovedInstructorAdditionalTARequests() {
        System.out.println("Fetching Instructor Additional TA Requests for dean");
        int receiverId = currentUserUtil.getCurrentUserId();
        return instructorAdditionalTARequestService.getUnapprovedInstructorAdditionalTARequests(receiverId);
    }
}
