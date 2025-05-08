package com.cs319group3.backend.Controllers.RequestControllers;

import com.cs319group3.backend.Components.CurrentUserUtil;
import com.cs319group3.backend.DTOs.RequestDTOs.RequestDTO;
import com.cs319group3.backend.Services.TAWorkloadRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("taWorkloadRequest")
@ComponentScan(basePackages = {"com.cs319group3.backend.Controllers"})
@CrossOrigin(origins = "http://localhost:3000")
public class TAWorkloadRequestController {

    @Autowired
    private TAWorkloadRequestService taWorkloadRequestService;
    @Autowired
    private CurrentUserUtil currentUserUtil;

    @PostMapping("create")
    public boolean createTAWorkloadRequest(@RequestBody RequestDTO taWorkloadRequestDTO, @RequestParam("id") int taId) {
        return taWorkloadRequestService.createTAWorkloadRequest(taWorkloadRequestDTO, taId);
    }

    @GetMapping("getByTA")
    public List<RequestDTO> getTAWorkloadRequestsByTA() {
        int userId = currentUserUtil.getCurrentUserId();
        return taWorkloadRequestService.getTAWorkloadRequestsByTA(userId);
    }

    @GetMapping("getByInstructor")
    public List<RequestDTO> getTAWorkloadRequestsByInstructor() {
        int userId = currentUserUtil.getCurrentUserId();
        return taWorkloadRequestService.getTAWorkloadRequestsByInstructor(userId);
    }
}
