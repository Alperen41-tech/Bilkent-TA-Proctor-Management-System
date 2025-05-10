package com.cs319group3.backend.Controllers.UserControllers;

import com.cs319group3.backend.Components.CurrentUserUtil;
import com.cs319group3.backend.DTOs.CreateTADTO;
import com.cs319group3.backend.DTOs.TAProfileDTO;
import com.cs319group3.backend.Repositories.ClassProctoringRepo;
import com.cs319group3.backend.Repositories.DepartmentSecretaryRepo;
import com.cs319group3.backend.Services.TAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsible for managing TA operations such as profile access,
 * creation, and availability retrieval based on department or faculty.
 */
@RestController
@RequestMapping("ta")
@ComponentScan(basePackages = {"com.cs319group3.backend.Controllers.UserControllers"})
@CrossOrigin(origins = "http://localhost:3000")
public class TAController {

    @Autowired
    private TAService taService;

    @Autowired
    private CurrentUserUtil currentUserUtil;

    @Autowired
    private DepartmentSecretaryRepo departmentSecretaryRepo;

    @Autowired
    private ClassProctoringRepo classProctoringRepo;

    /**
     * Retrieves the profile of the currently logged-in TA.
     *
     * @return TAProfileDTO for the current user
     */
    @GetMapping("profile")
    public TAProfileDTO getTAProfile() {
        System.out.println("request received");
        int id = currentUserUtil.getCurrentUserId();
        return taService.getTAProfileById(id);
    }

    /**
     * Retrieves the profile of a TA by their ID.
     *
     * @param id the TA ID
     * @return TAProfileDTO of the specified TA
     */
    @GetMapping("profileById")
    public TAProfileDTO getTAProfile(@RequestParam int id) {
        System.out.println("request received");
        return taService.getTAProfileById(id);
    }

    /**
     * Creates a new TA account.
     *
     * @param dto the creation data for the TA
     * @return true if creation was successful
     */
    @PostMapping("createTA")
    public boolean createTA(@RequestBody CreateTADTO dto) {
        System.out.println("create request received");
        return taService.createNewTA(dto);
    }

    /**
     * Retrieves all TA profiles.
     *
     * @return list of all TAProfileDTOs
     */
    @GetMapping("getAllTAProfiles")
    public List<TAProfileDTO> getAllTAProfiles() {
        return taService.getAllTAProfiles();
    }

    /**
     * Retrieves available TAs from the same department, excluding the given proctoring.
     *
     * @param proctoringId proctoring to exclude
     * @return list of TAProfileDTOs
     */
    @GetMapping("getAvailableTAsByDepartmentExceptProctoring")
    public List<TAProfileDTO> getAllAvailableTAsByDepartment(@RequestParam int proctoringId) {
        int userId = currentUserUtil.getCurrentUserId();
        System.out.println("Getting available TA profiles by department except in proctoring " + proctoringId);
        String departmentCode = classProctoringRepo.findByClassProctoringId(proctoringId).get().getCourse().getDepartment().getDepartmentCode();
        return taService.getAllAvailableTAsByDepartmentCode(departmentCode,proctoringId, userId);
    }

    @GetMapping("getAvailableTAsByDepartmentExceptProctoringSecretary")
    public List<TAProfileDTO> getAllAvailableTAsByDepartmentForSecretary(@RequestParam int proctoringId) {
        int userId = currentUserUtil.getCurrentUserId();
        System.out.println("Getting available TA profiles by department except in proctoring " + proctoringId);
        String departmentCode = departmentSecretaryRepo.findByUserId(userId).get().getDepartment().getDepartmentCode();
        return taService.getAllAvailableTAsByDepartmentCode(departmentCode, proctoringId, userId);
    }

    /**
     * Retrieves available TAs from the same faculty, excluding the given proctoring.
     *
     * @param facultyId faculty ID to filter
     * @param proctoringId proctoring to exclude
     * @return list of TAProfileDTOs
     */
    @GetMapping("getAvailableTAsByFacultyExceptProctoring")
    public List<TAProfileDTO> getAllAvailableTAsByFaculty(@RequestParam int facultyId, @RequestParam int proctoringId) {
        int userId = currentUserUtil.getCurrentUserId();
        System.out.println("Getting available TA profiles by Faculty");
        return taService.getAllAvailableTAsByFacultyId(facultyId, proctoringId, userId);
    }

    /**
     * Retrieves available TAs from the department with additional eligibility and one-day restrictions.
     *
     * @param departmentCode department code to filter
     * @param proctoringId proctoring to exclude
     * @param eligibilityRestriction filter by eligibility
     * @param oneDayRestriction filter by one-day limit
     * @return list of TAProfileDTOs
     */
    @GetMapping("getAvailableTAsByDepartmentExceptProctoringWithRestriction")
    public List<TAProfileDTO> getAllAvailableTAsByDepartment(@RequestParam String departmentCode,
                                                             @RequestParam int proctoringId,
                                                             @RequestParam boolean eligibilityRestriction,
                                                             @RequestParam boolean oneDayRestriction) {
        System.out.println("Getting available TA profiles by department except in proctoring " + proctoringId);
        int userId = currentUserUtil.getCurrentUserId();
        return taService.getAllAvailableTAsByDepartmentCode(departmentCode, proctoringId, userId, eligibilityRestriction, oneDayRestriction);
    }

    /**
     * Retrieves available TAs from the faculty with additional eligibility and one-day restrictions.
     *
     * @param facultyId faculty ID to filter
     * @param proctoringId proctoring to exclude
     * @param eligibilityRestriction filter by eligibility
     * @param oneDayRestriction filter by one-day limit
     * @return list of TAProfileDTOs
     */
    @GetMapping("getAvailableTAsByFacultyExceptProctoringWithRestriction")
    public List<TAProfileDTO> getAllAvailableTAsByFaculty(@RequestParam int facultyId,
                                                          @RequestParam int proctoringId,
                                                          @RequestParam boolean eligibilityRestriction,
                                                          @RequestParam boolean oneDayRestriction) {
        System.out.println("Getting available TA profiles by Faculty");
        int userId = currentUserUtil.getCurrentUserId();
        return taService.getAllAvailableTAsByFacultyId(facultyId, proctoringId, userId, eligibilityRestriction, oneDayRestriction);
    }
}