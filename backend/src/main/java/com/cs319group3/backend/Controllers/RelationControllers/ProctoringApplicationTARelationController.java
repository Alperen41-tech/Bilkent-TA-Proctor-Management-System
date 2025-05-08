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


    @PostMapping("create")
    public ResponseEntity<Boolean> createProctoringApplicationTARelation(@RequestParam(name = "applicationId") int proctoringApplicationId ) {
        int taId = currentUserUtil.getCurrentUserId();
        return proctoringApplicationTARelationService.createProctoringApplicationTARelation(proctoringApplicationId, taId);
    }


    @GetMapping("getApplicantsForApplication")
    public List<TAProfileDTO> getApplicants(@RequestParam("applicationId") int applicationId) {
        return proctoringApplicationTARelationService.getAllApplicants(applicationId);
    }


    @GetMapping("getApplicantCountForProctoring")
    public int getApplicantCount(@RequestParam("applicationId") int applicationId) {
        return proctoringApplicationTARelationService.getApplicantCount(applicationId);
    }









}
