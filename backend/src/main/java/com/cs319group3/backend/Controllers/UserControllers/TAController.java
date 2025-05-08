package com.cs319group3.backend.Controllers.UserControllers;



import com.cs319group3.backend.DTOs.CreateTADTO;
import com.cs319group3.backend.DTOs.TAProfileDTO;
import com.cs319group3.backend.Services.TAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("ta")
@ComponentScan(basePackages = {"com.cs319group3.backend.Controllers.UserControllers"})
@CrossOrigin(origins = "http://localhost:3000")
public class TAController {

    @Autowired
    private TAService taService;

    @GetMapping("profile")
    public TAProfileDTO getTAProfile(){
        System.out.println("request received");
        return taService.getTAProfileById();
    }

    @PostMapping("createTA")
    public boolean createTA(@RequestBody CreateTADTO dto) {
        System.out.println("create request received");
        return taService.createNewTA(dto);
    }

    @GetMapping("getAllTAProfiles")
    public List<TAProfileDTO> getAllTAProfiles() {
        return taService.getAllTAProfiles();
    }

    @GetMapping("getAvailableTAsByDepartmentExceptProctoring")
    public List<TAProfileDTO> getAllAvailableTAsByDepartment(@RequestParam String departmentCode,@RequestParam int proctoringId, @RequestParam int userId) {
        System.out.println("Getting available TA profiles by department except in proctoring " + proctoringId);
        return taService.getAllAvailableTAsByDepartmentCode(departmentCode, proctoringId, userId);
    }

    @GetMapping("getAvailableTAsByFacultyExceptProctoring")
    public List<TAProfileDTO> getAllAvailableTAsByFaculty(@RequestParam int facultyId, @RequestParam int proctoringId, @RequestParam int userId) {
        System.out.println("Getting available TA profiles by Faculty");
        return taService.getAllAvailableTAsByFacultyId(facultyId, proctoringId, userId);
    }

    @GetMapping("getAvailableTAsByDepartmentExceptProctoringWithRestriction")
    public List<TAProfileDTO> getAllAvailableTAsByDepartment(@RequestParam String departmentCode,@RequestParam int proctoringId, @RequestParam int userId, @RequestParam boolean eligibilityRestriction, @RequestParam boolean oneDayRestriction) {
        System.out.println("Getting available TA profiles by department except in proctoring " + proctoringId);
        return taService.getAllAvailableTAsByDepartmentCode(departmentCode, proctoringId, userId, eligibilityRestriction, oneDayRestriction);
    }

    @GetMapping("getAvailableTAsByFacultyExceptProctoringWithRestriction")
    public List<TAProfileDTO> getAllAvailableTAsByFaculty(@RequestParam int facultyId, @RequestParam int proctoringId, @RequestParam int userId, @RequestParam boolean eligibilityRestriction, @RequestParam boolean oneDayRestriction) {
        System.out.println("Getting available TA profiles by Faculty");
        return taService.getAllAvailableTAsByFacultyId(facultyId, proctoringId, userId, eligibilityRestriction, oneDayRestriction);
    }
}
