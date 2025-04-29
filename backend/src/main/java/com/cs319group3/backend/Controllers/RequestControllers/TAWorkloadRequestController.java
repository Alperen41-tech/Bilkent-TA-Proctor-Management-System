package com.cs319group3.backend.Controllers.RequestControllers;

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

    @PostMapping("create")
    public boolean createTAWorkloadRequest(@RequestBody RequestDTO taWorkloadRequestDTO, @RequestParam("id") int taId) {
        return taWorkloadRequestService.createTAWorkloadRequest(taWorkloadRequestDTO, taId);
    }

    @GetMapping("getByTA")
    public List<RequestDTO> getTAWorkloadRequestsByTA(@RequestParam("id") int taId) {
        return taWorkloadRequestService.getTAWorkloadRequestsByTA(taId);
    }

    @GetMapping("getByInstructor")
    public List<RequestDTO> getTAWorkloadRequestsByInstructor(@RequestParam("instructorId") int instructorId) {
        return taWorkloadRequestService.getTAWorkloadRequestsByInstructor(instructorId);
    }
}
