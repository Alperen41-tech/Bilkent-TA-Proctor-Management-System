package com.cs319group3.backend.Controllers.RequestControllers;

import com.cs319group3.backend.Components.CurrentUserUtil;
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
    private AuthStaffProctoringRequestService authStaffProctoringRequestService;

    @Autowired
    private CurrentUserUtil currentUserUtil;

    /**
     * Sends a non-forced authorization staff proctoring request to a TA.
     *
     * @param classProctoringId the ID of the class proctoring
     * @param taId the ID of the target TA
     * @return true if the request was sent successfully
     */
    @PostMapping("sendAuthStaffProctoringRequest")
    public boolean sendAuthStaffProctoringRequest(@RequestParam int classProctoringId, @RequestParam int taId) {
        int senderId = currentUserUtil.getCurrentUserId();
        return authStaffProctoringRequestService.sendAuthStaffProctoringRequest(classProctoringId, taId, senderId, false);
    }

    /**
     * Sends a forced authorization staff proctoring request to a TA.
     *
     * @param classProctoringId the ID of the class proctoring
     * @param taId the ID of the target TA
     * @return true if the request was sent successfully
     */
    @PostMapping("forceAuthStaffProctoringRequest")
    public boolean forceAuthStaffProctoringRequest(@RequestParam int classProctoringId, @RequestParam int taId) {
        int senderId = currentUserUtil.getCurrentUserId();
        return authStaffProctoringRequestService.sendAuthStaffProctoringRequest(classProctoringId, taId, senderId, true);
    }

    /**
     * Automatically selects and sends unforced proctoring requests to TAs in a department.
     *
     * @param classProctoringId the ID of the class proctoring
     * @param departmentCode the code of the department
     * @param count the number of TAs to select
     * @param eligibilityRestriction whether to restrict by eligibility
     * @param oneDayRestriction whether to restrict by one-day limit
     * @return a list of TA profiles selected
     */
    @GetMapping("selectAuthStaffProctoringRequestAutomaticallyInDepartment")
    public List<TAProfileDTO> selectAuthStaffProctoringRequestAutomaticallyInDepartment(@RequestParam int classProctoringId, @RequestParam String departmentCode, @RequestParam int count, @RequestParam boolean eligibilityRestriction, @RequestParam boolean oneDayRestriction) {
        System.out.println("Class proctoring request sent.");
        int senderId = currentUserUtil.getCurrentUserId();
        return authStaffProctoringRequestService.sendAuthStaffProctoringRequestAutomaticallyInDepartment(classProctoringId, departmentCode, senderId, count, eligibilityRestriction, oneDayRestriction);
    }

    /**
     * Automatically selects and sends unforced proctoring requests to TAs in a faculty.
     *
     * @param classProctoringId the ID of the class proctoring
     * @param facultyId the ID of the faculty
     * @param senderId the ID of the sender (staff)
     * @param count the number of TAs to select
     * @param eligibilityRestriction whether to restrict by eligibility
     * @param oneDayRestriction whether to restrict by one-day limit
     * @return a list of TA profiles selected
     */
    @GetMapping("selectAuthStaffProctoringRequestAutomaticallyInFaculty")
    public List<TAProfileDTO> selectAuthStaffProctoringRequestAutomaticallyInFaculty(@RequestParam int classProctoringId, @RequestParam int facultyId, @RequestParam int senderId, @RequestParam int count, @RequestParam boolean eligibilityRestriction, @RequestParam boolean oneDayRestriction) {
        System.out.println("Class proctoring request sent.");
        return authStaffProctoringRequestService.sendAuthStaffProctoringRequestAutomaticallyInFaculty(classProctoringId, facultyId, senderId, count, eligibilityRestriction, oneDayRestriction);
    }

    /**
     * Sends unforced proctoring requests to a given list of TAs.
     *
     * @param dtoList the list of TA profiles
     * @param classProctoringId the ID of the class proctoring
     * @return true if all requests were sent successfully
     */
    @PostMapping("unforcedAssign")
    public boolean unforcedAssign(@RequestBody List<TAProfileDTO> dtoList, @RequestParam int classProctoringId, @RequestParam int senderId) {
        System.out.println("Force assign request sent.");
        //int senderId = currentUserUtil.getCurrentUserId();
        return authStaffProctoringRequestService.sendAuthStaffProctoringRequests(dtoList, classProctoringId, senderId, false);
    }

    /**
     * Sends forced proctoring requests to a given list of TAs.
     *
     * @param dtoList the list of TA profiles
     * @param classProctoringId the ID of the class proctoring
     * @return true if all requests were sent successfully
     */
    @PostMapping("forcedAssign")
    public boolean forcedAssign(@RequestBody List<TAProfileDTO> dtoList, @RequestParam int classProctoringId) {
        System.out.println("Force assign request sent.");
        int senderId = currentUserUtil.getCurrentUserId();
        return authStaffProctoringRequestService.sendAuthStaffProctoringRequests(dtoList, classProctoringId, senderId, true);
    }
}