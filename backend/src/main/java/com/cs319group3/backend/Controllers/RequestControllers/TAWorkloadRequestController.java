package com.cs319group3.backend.Controllers.RequestControllers;

import com.cs319group3.backend.DTOs.TAWorkloadRequestDTO;
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
    public boolean createTAWorkloadRequest(@RequestBody TAWorkloadRequestDTO taWorkloadRequestDTO, @RequestParam("id") int taId) {
        return taWorkloadRequestService.createTAWorkloadRequest(taWorkloadRequestDTO, taId);
    }

    @GetMapping("get")
    public List<TAWorkloadRequestDTO> getTAWorkloadRequests(@RequestParam("id") int taId) {
        return taWorkloadRequestService.getTAWorkloadRequests(taId);
    }
}
