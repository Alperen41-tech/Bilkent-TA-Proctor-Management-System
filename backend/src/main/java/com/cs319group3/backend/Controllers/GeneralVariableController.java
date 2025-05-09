package com.cs319group3.backend.Controllers;

import com.cs319group3.backend.DTOs.GeneralVariableDTO;
import com.cs319group3.backend.Services.GeneralVariableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

/**
 * Controller responsible for managing global system variables such as
 * semester and proctoring cap.
 */
@RestController
@RequestMapping("generalVariable")
@CrossOrigin(origins = "http://localhost:3000")
public class GeneralVariableController {

    @Autowired
    private GeneralVariableService generalVariableService;

    /**
     * Changes the currently active semester.
     *
     * @param semester the new semester string
     * @return true if the semester was successfully updated
     */
    @PostMapping("changeSemester")
    public boolean changeSemester(@RequestParam String semester) {
        return generalVariableService.changeSemester(semester);
    }

    /**
     * Changes the maximum number of proctoring assignments allowed.
     *
     * @param proctoringCap the new cap value
     * @return true if the cap was successfully updated
     */
    @PostMapping("changeProctoringCap")
    public boolean changeProctoringCap(@RequestParam int proctoringCap) {
        return generalVariableService.changeProctoringCap(proctoringCap);
    }

    /**
     * Retrieves the current general variable settings.
     *
     * @return a DTO containing current general variable values
     */
    @GetMapping("getGeneralVariable")
    public GeneralVariableDTO getGeneralVariable() {
        return generalVariableService.getGeneralVariable();
    }
}