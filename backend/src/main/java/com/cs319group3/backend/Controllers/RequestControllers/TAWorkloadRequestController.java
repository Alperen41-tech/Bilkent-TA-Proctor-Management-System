package com.cs319group3.backend.Controllers.RequestControllers;

import com.cs319group3.backend.Components.CurrentUserUtil;
import com.cs319group3.backend.DTOs.RequestDTOs.RequestDTO;
import com.cs319group3.backend.Services.TAWorkloadRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsible for managing TA workload request operations,
 * including creation and retrieval by TA or instructor.
 */
@RestController
@RequestMapping("taWorkloadRequest")
@ComponentScan(basePackages = {"com.cs319group3.backend.Controllers"})
@CrossOrigin(origins = "http://localhost:3000")
public class TAWorkloadRequestController {

    @Autowired
    private TAWorkloadRequestService taWorkloadRequestService;

    @Autowired
    private CurrentUserUtil currentUserUtil;

    /**
     * Creates a workload request from the currently logged-in TA.
     *
     * @param taWorkloadRequestDTO the request payload from the TA
     * @return true if the request was created successfully
     */
    @PostMapping("create")
    public boolean createTAWorkloadRequest(@RequestBody RequestDTO taWorkloadRequestDTO) {
        int taId = currentUserUtil.getCurrentUserId();
        return taWorkloadRequestService.createTAWorkloadRequest(taWorkloadRequestDTO, taId);
    }

    /**
     * Retrieves all workload requests submitted by the currently logged-in TA.
     *
     * @return a list of RequestDTOs submitted by the TA
     */
    @GetMapping("getByTA")
    public List<RequestDTO> getTAWorkloadRequestsByTA() {
        int userId = currentUserUtil.getCurrentUserId();
        return taWorkloadRequestService.getTAWorkloadRequestsByTA(userId);
    }

    /**
     * Retrieves all workload requests that are visible to the currently logged-in instructor.
     *
     * @return a list of RequestDTOs submitted by TAs under this instructor
     */
    @GetMapping("getByInstructor")
    public List<RequestDTO> getTAWorkloadRequestsByInstructor() {
        int userId = currentUserUtil.getCurrentUserId();
        return taWorkloadRequestService.getTAWorkloadRequestsByInstructor(userId);
    }
}