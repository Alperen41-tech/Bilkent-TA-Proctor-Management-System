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

    /**
     * Retrieves all approved additional TA requests sent to the currently logged-in receiver (e.g., a dean).
     *
     * @return a list of approved instructor additional TA request DTOs
     */
    @GetMapping("getApprovedInstructorAdditionalTARequests")
    public List<RequestDTO> getApprovedInstructorAdditionalTARequests() {
        int receiverId = currentUserUtil.getCurrentUserId();
        System.out.println("Fetching Instructor Additional TA Requests for dean");
        return instructorAdditionalTARequestService.getApprovedInstructorAdditionalTARequests(receiverId);
    }

    /**
     * Retrieves all unapproved additional TA requests sent to the currently logged-in receiver (e.g., a dean).
     *
     * @return a list of unapproved instructor additional TA request DTOs
     */
    @GetMapping("getUnapprovedInstructorAdditionalTARequests")
    public List<RequestDTO> getUnapprovedInstructorAdditionalTARequests() {
        System.out.println("Fetching Instructor Additional TA Requests for dean");
        int receiverId = currentUserUtil.getCurrentUserId();
        return instructorAdditionalTARequestService.getUnapprovedInstructorAdditionalTARequests(receiverId);
    }

    /**
     * Creates a new additional TA request sent by the currently logged-in instructor.
     *
     * @param requestDTO the request data to be submitted
     * @return true if the request was created successfully
     */
    @PostMapping("createInstructorAdditionalTARequest")
    public boolean createInstructorAdditionalTARequest(@RequestBody RequestDTO requestDTO) {
        int senderId = currentUserUtil.getCurrentUserId();
        return instructorAdditionalTARequestService.createInstructorAdditionalTARequest(requestDTO, senderId);
    }

}