package com.cs319group3.backend.Controllers;

import com.cs319group3.backend.DTOs.ProctoringApplicationDTO;
import com.cs319group3.backend.Services.ProctoringApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("proctoringApplication")
@CrossOrigin(origins = "http://localhost:3000")
public class ProctoringApplicationController {

    @Autowired
    ProctoringApplicationService proctoringApplicationService;

    @GetMapping("getProctoringApplications")
    public List<ProctoringApplicationDTO> getProctoringApplications(@RequestParam int deansOfficeId) {
        System.out.println("getProctoringApplications is called");
        return proctoringApplicationService.getProctoringApplications(deansOfficeId);
    }

    @PostMapping("createProctoringApplications")
    public boolean createProctoringApplication(@RequestParam int classProctoringId, @RequestBody List<ProctoringApplicationDTO> proctoringApplicationDTO) {
        System.out.println("createProctoringApplication is called");
        return proctoringApplicationService.createProctoringApplications(classProctoringId, proctoringApplicationDTO);
    }
}
