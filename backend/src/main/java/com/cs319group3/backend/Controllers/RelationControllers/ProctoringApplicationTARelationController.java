package com.cs319group3.backend.Controllers.RelationControllers;

import com.cs319group3.backend.Components.CurrentUserUtil;
import com.cs319group3.backend.DTOs.ProctoringApplicationDTO;
import com.cs319group3.backend.DTOs.TAProfileDTO;
import com.cs319group3.backend.Services.ProctoringApplicationTARelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("proctoringApplicationTARelation")
@CrossOrigin(origins = "http://localhost:3000")
public class ProctoringApplicationTARelationController {

    @Autowired
    private ProctoringApplicationTARelationService proctoringApplicationTARelationService;
    @Autowired
    private CurrentUserUtil currentUserUtil;

    /**
     * Creates a relation between the currently logged-in TA and the specified proctoring application.
     *
     * @param proctoringApplicationId the ID of the proctoring application
     * @return ResponseEntity containing true if creation was successful, false otherwise
     */
    @PostMapping("create")
    public ResponseEntity<Boolean> createProctoringApplicationTARelation(@RequestParam(name = "applicationId") int proctoringApplicationId) {
        int taId = currentUserUtil.getCurrentUserId();
        return proctoringApplicationTARelationService.createProctoringApplicationTARelation(proctoringApplicationId, taId);
    }

    /**
     * Retrieves a list of TA profiles who have applied for the specified proctoring application.
     *
     * @param applicationId the ID of the proctoring application
     * @return list of TAProfileDTOs representing the applicants
     */
    @GetMapping("getApplicantsForApplication")
    public List<TAProfileDTO> getApplicants(@RequestParam("applicationId") int applicationId) {
        System.out.println("Getting applicants for application");
        return proctoringApplicationTARelationService.getAllApplicants(applicationId);
    }

    /**
     * Returns the number of applicants for a specified proctoring application.
     *
     * @param applicationId the ID of the proctoring application
     * @return number of applicants
     */
    @GetMapping("getApplicantCountForProctoring")
    public int getApplicantCount(@RequestParam("applicationId") int applicationId) {
        return proctoringApplicationTARelationService.getApplicantCount(applicationId);
    }

}