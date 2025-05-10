package com.cs319group3.backend.Controllers.RequestControllers;

import com.cs319group3.backend.Components.CurrentUserUtil;
import com.cs319group3.backend.DTOs.RequestDTOs.RequestDTO;
import com.cs319group3.backend.Services.TALeaveRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("taLeaveRequest")
@ComponentScan(basePackages = {"com.cs319group3.backend.Controllers"})
@CrossOrigin(origins = "http://localhost:3000")
public class TALeaveRequestController {

    @Autowired
    private TALeaveRequestService taLeaveRequestService;

    @Autowired
    private CurrentUserUtil currentUserUtil;

    /**
     * Creates a new leave request for the currently logged-in TA.
     *
     * @param taLeaveRequest the leave request data submitted by the TA
     * @return true if the request was successfully created
     */
    @PostMapping("create")
    public boolean createTALeaveRequest(@RequestBody RequestDTO taLeaveRequest) throws Exception {
        int taId = currentUserUtil.getCurrentUserId();
        return taLeaveRequestService.createTALeaveRequest(taLeaveRequest, taId);
    }
}