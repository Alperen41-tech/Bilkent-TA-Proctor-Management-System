package com.cs319group3.backend.Controllers;

import com.cs319group3.backend.Components.CurrentUserUtil;
import com.cs319group3.backend.DTOs.ProctoringApplicationDTO;
import com.cs319group3.backend.Enums.ProctoringApplicationType;
import com.cs319group3.backend.Services.ProctoringApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsible for managing proctoring applications submitted by TAs
 * and reviewed by Dean's Office or department staff.
 */
@RestController
@RequestMapping("proctoringApplication")
@CrossOrigin(origins = "http://localhost:3000")
public class ProctoringApplicationController {

    @Autowired
    private ProctoringApplicationService proctoringApplicationService;

    @Autowired
    private CurrentUserUtil currentUserUtil;

    /**
     * Retrieves proctoring applications submitted to the currently logged-in Dean's Office user.
     *
     * @return a list of ProctoringApplicationDTOs
     */
    @GetMapping("getProctoringApplications")
    public List<ProctoringApplicationDTO> getProctoringApplications() {
        System.out.println("getProctoringApplications is called");
        int deansOfficeId = currentUserUtil.getCurrentUserId();
        return proctoringApplicationService.getProctoringApplications(deansOfficeId);
    }

    /**
     * Retrieves all proctoring applications within a specific department.
     *
     * @return a list of ProctoringApplicationDTOs
     */
    @GetMapping("getAllApplicationsByDepartment")
    public List<ProctoringApplicationDTO> getAllApplicationsByDepartment() {
        int departmentSecretaryId = currentUserUtil.getCurrentUserId();
        System.out.println("The department secretary is: " + departmentSecretaryId);
        return proctoringApplicationService.getAllApplicationsByDepartment(departmentSecretaryId);
    }

    /**
     * Retrieves all proctoring applications available for the currently logged-in TA
     * filtered by a specific application type.
     *
     * @param applicationType the type of application to filter
     * @return a list of ProctoringApplicationDTOs
     */
    @GetMapping("getAllApplicationsForTA")
    public List<ProctoringApplicationDTO> getAllApplicationsForTA(@RequestParam("applicationType") ProctoringApplicationType applicationType) {
        int userId = currentUserUtil.getCurrentUserId();
        return proctoringApplicationService.getAllApplicationsForTA(userId, applicationType);
    }

    /**
     * Creates multiple proctoring applications associated with a specific class proctoring.
     *
     * @param classProctoringId the ID of the class proctoring
     * @param proctoringApplicationDTO the list of applications to create
     * @return true if creation is successful
     */
    @PostMapping("createProctoringApplications")
    public boolean createProctoringApplication(@RequestParam int requestId, @RequestParam int classProctoringId, @RequestBody List<ProctoringApplicationDTO> proctoringApplicationDTO) {
        System.out.println("createProctoringApplication is called");
        int deansOfficeId = currentUserUtil.getCurrentUserId();
        return proctoringApplicationService.createProctoringApplications(requestId, classProctoringId, proctoringApplicationDTO, deansOfficeId);
    }

    /**
     * Updates the type of a specific proctoring application.
     *
     * @param applicationId the application ID
     * @param type the new application type
     * @return true if update is successful
     */
    @PutMapping("setApplicationType")
    public boolean setApplicationType (@RequestParam("applicationId") int applicationId, @RequestParam("applicationType") ProctoringApplicationType type) {
        return proctoringApplicationService.setApplicationType(applicationId, type);
    }

    @PutMapping("setComplete")
    public boolean setComplete(@RequestParam int applicationId){
        System.out.println("setApproved is called");
        return proctoringApplicationService.setComplete(applicationId);
    }
}