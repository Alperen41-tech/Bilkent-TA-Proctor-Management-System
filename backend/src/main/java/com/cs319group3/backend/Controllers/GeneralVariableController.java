package com.cs319group3.backend.Controllers;

import com.cs319group3.backend.DTOs.GeneralVariableDTO;
import com.cs319group3.backend.Services.GeneralVariableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("generalVariable")
@ComponentScan(basePackages = {"com.cs319group3.backend.Controllers"})
@CrossOrigin(origins = "http://localhost:3000")
public class GeneralVariableController {

    @Autowired
    GeneralVariableService generalVariableService;

    @PostMapping("changeSemester")
    public boolean changeSemester(@RequestParam String semester) {
        return generalVariableService.changeSemester(semester);
    }

    @PostMapping("changeProctoringCap")
    public boolean changeProctoringCap(@RequestParam int proctoringCap) {
        return generalVariableService.changeProctoringCap(proctoringCap);
    }
    
    @GetMapping("getGeneralVariable")
    public GeneralVariableDTO getGeneralVariable() {
        return generalVariableService.getGeneralVariable();
    }
}
