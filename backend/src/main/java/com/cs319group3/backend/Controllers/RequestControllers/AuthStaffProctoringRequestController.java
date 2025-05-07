package com.cs319group3.backend.Controllers.RequestControllers;

import com.cs319group3.backend.DTOs.TAProfileDTO;
import com.cs319group3.backend.Entities.ClassProctoring;
import com.cs319group3.backend.Entities.RelationEntities.ClassProctoringTARelation;
import com.cs319group3.backend.Entities.UserEntities.TA;
import com.cs319group3.backend.Repositories.ClassProctoringTARelationRepo;
import com.cs319group3.backend.Services.AuthStaffProctoringRequestService;
import com.cs319group3.backend.Services.AuthenticationService;
import com.cs319group3.backend.Services.ClassProctoringTARelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("authStaffProctoringRequestController")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthStaffProctoringRequestController {

    @Autowired
    AuthStaffProctoringRequestService authStaffProctoringRequestService;

    @PostMapping("sendAuthStaffProctoringRequest")
    public boolean sendAuthStaffProctoringRequest(@RequestParam int classProctoringId, @RequestParam int taId,@RequestParam int senderId) {
        System.out.println("Class proctoring request sent.");
        return authStaffProctoringRequestService.sendAuthStaffProctoringRequest(classProctoringId, taId, senderId, false);
    }

    @Autowired
    ClassProctoringTARelationService classProctoringTARelation;

    @PostMapping("forceAuthStaffProctoringRequest")
    public boolean forceAuthStaffProctoringRequest(@RequestParam int classProctoringId, @RequestParam int taId, @RequestParam int senderId) {
        System.out.println("Force auth staff request sent.");
        return authStaffProctoringRequestService.sendAuthStaffProctoringRequest(classProctoringId, taId, senderId, true);
    }

    @GetMapping("selectAuthStaffProctoringRequestAutomaticallyInDepartment")
    public List<TAProfileDTO> selectAuthStaffProctoringRequestAutomaticallyInDepartment(@RequestParam int classProctoringId, @RequestParam String departmentCode, @RequestParam int senderId, @RequestParam int count, @RequestParam boolean eligibilityRestriction, @RequestParam boolean oneDayRestriction) {
        System.out.println("Class proctoring request sent.");
        return authStaffProctoringRequestService.sendAuthStaffProctoringRequestAutomaticallyInDepartment(classProctoringId, departmentCode, senderId, count, eligibilityRestriction, oneDayRestriction);
    }

    @GetMapping("selectAuthStaffProctoringRequestAutomaticallyInFaculty")
    public List<TAProfileDTO> selectAuthStaffProctoringRequestAutomaticallyInFaculty(@RequestParam int classProctoringId, @RequestParam int facultyId, @RequestParam int senderId, @RequestParam int count, @RequestParam boolean eligibilityRestriction, @RequestParam boolean oneDayRestriction) {
        System.out.println("Class proctoring request sent.");
        return authStaffProctoringRequestService.sendAuthStaffProctoringRequestAutomaticallyInFaculty(classProctoringId, facultyId, senderId, count, eligibilityRestriction, oneDayRestriction);
    }

    @PostMapping("unforcedAssign")
    public boolean unforcedAssign(@RequestBody List<TAProfileDTO> dtoList, @RequestParam int classProctoringId, @RequestParam int senderId) {
        System.out.println("Force assign request sent.");
        return authStaffProctoringRequestService.sendAuthStaffProctoringRequests(dtoList, classProctoringId, senderId, false);
    }

    @PostMapping("forcedAssign")
    public boolean forcedAssign(@RequestBody List<TAProfileDTO> dtoList, @RequestParam int classProctoringId, @RequestParam int senderId) {
        System.out.println("Force assign request sent.");
        return authStaffProctoringRequestService.sendAuthStaffProctoringRequests(dtoList, classProctoringId, senderId, true);
    }
}
