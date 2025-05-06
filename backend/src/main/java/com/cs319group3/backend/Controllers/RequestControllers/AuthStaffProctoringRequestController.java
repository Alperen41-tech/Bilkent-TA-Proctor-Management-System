package com.cs319group3.backend.Controllers.RequestControllers;

import com.cs319group3.backend.Entities.ClassProctoring;
import com.cs319group3.backend.Entities.RelationEntities.ClassProctoringTARelation;
import com.cs319group3.backend.Entities.UserEntities.TA;
import com.cs319group3.backend.Repositories.ClassProctoringTARelationRepo;
import com.cs319group3.backend.Services.AuthStaffProctoringRequestService;
import com.cs319group3.backend.Services.AuthenticationService;
import com.cs319group3.backend.Services.ClassProctoringTARelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("sendAuthStaffProctoringRequestAutomaticallyInDepartment")
    public boolean sendAuthStaffProctoringRequestAutomaticallyInDepartment(@RequestParam int classProctoringId, @RequestParam String departmentCode, @RequestParam int senderId, @RequestParam int count, @RequestParam boolean eligibilityRestriction, @RequestParam boolean oneDayRestriction) {
        System.out.println("Class proctoring request sent.");
        return authStaffProctoringRequestService.sendAuthStaffProctoringRequestAutomaticallyInDepartment(classProctoringId, departmentCode, senderId, count, eligibilityRestriction, oneDayRestriction, false);
    }

    @PostMapping("sendAuthStaffProctoringRequestAutomaticallyInFaculty")
    public boolean sendAuthStaffProctoringRequestAutomaticallyInFaculty(@RequestParam int classProctoringId, @RequestParam int facultyId, @RequestParam int senderId, @RequestParam int count, @RequestParam boolean eligibilityRestriction, @RequestParam boolean oneDayRestriction) {
        System.out.println("Class proctoring request sent.");
        return authStaffProctoringRequestService.sendAuthStaffProctoringRequestAutomaticallyInFaculty(classProctoringId, facultyId, senderId, count, eligibilityRestriction, oneDayRestriction, false);
    }

    @PostMapping("forceAuthStaffProctoringRequestAutomaticallyInDepartment")
    public boolean forceAuthStaffProctoringRequestAutomaticallyInDepartment(@RequestParam int classProctoringId,@RequestParam String departmentCode, @RequestParam int senderId, @RequestParam int count, @RequestParam boolean eligibilityRestriction, @RequestParam boolean oneDayRestriction) {

        return authStaffProctoringRequestService.sendAuthStaffProctoringRequestAutomaticallyInDepartment(classProctoringId, departmentCode, senderId, count, eligibilityRestriction, oneDayRestriction, true);
    }

    @PostMapping("forceAuthStaffProctoringRequestAutomaticallyInFaculty")
    public boolean forceAuthStaffProctoringRequestAutomaticallyInFaculty( @RequestParam int classProctoringId, @RequestParam int facultyId, @RequestParam int senderId, @RequestParam int count, @RequestParam boolean eligibilityRestriction, @RequestParam boolean oneDayRestriction) {

        return authStaffProctoringRequestService.sendAuthStaffProctoringRequestAutomaticallyInFaculty(classProctoringId, facultyId, senderId, count, eligibilityRestriction, oneDayRestriction, true);
    }
}
