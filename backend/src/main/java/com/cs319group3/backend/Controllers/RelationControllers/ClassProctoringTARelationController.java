package com.cs319group3.backend.Controllers.RelationControllers;

import com.cs319group3.backend.Components.CurrentUserUtil;
import com.cs319group3.backend.DTOs.ClassProctoringAndTAsDTO;
import com.cs319group3.backend.DTOs.ClassProctoringTARelationDTO;
import com.cs319group3.backend.DTOs.TAProfileDTO;
import com.cs319group3.backend.Services.ClassProctoringAndTAsService;
import com.cs319group3.backend.Services.ClassProctoringTARelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("classProctoringTARelation")
@ComponentScan(basePackages = {"com.cs319group3.backend.Controllers.RelationControllers"})
@CrossOrigin(origins = "http://localhost:3000")
public class ClassProctoringTARelationController {

    @Autowired
    private ClassProctoringTARelationService classProctoringTARelationService;
    @Autowired
    private ClassProctoringAndTAsService classProctoringAndTAs;
    @Autowired
    private CurrentUserUtil currentUserUtil;

    /**
     * Retrieves the class proctorings assigned to the currently logged-in TA.
     *
     * @return a list of ClassProctoringTARelationDTOs
     */
    @GetMapping("getTAsClassProctorings")
    public List<ClassProctoringTARelationDTO> getTAsClassProctorings() throws Exception {
        int id = currentUserUtil.getCurrentUserId();
        return classProctoringTARelationService.getTAsClassProctoringDTOs(id);
    }

    /**
     * Retrieves class proctorings of the current TA filtered by their department.
     *
     * @return a list of ClassProctoringTARelationDTOs
     */
    @GetMapping("getTAsClassProctoringsByDepartment")
    public List<ClassProctoringTARelationDTO> getTAsClassProctoringsByDepartment() throws Exception {
            int userId = currentUserUtil.getCurrentUserId();
            userId = 1;
            return classProctoringTARelationService.getTAsClassProctoringsByDepartment(userId);
    }

    /**
     * Updates a TA's class proctoring record.
     *
     * @param classProctoringTARelationDTO the DTO containing updated information
     * @return true if the update was successful, false otherwise
     */
    @PutMapping("updateTAsClassProctorings")
    public boolean updateTAsClassProctorings(@RequestBody ClassProctoringTARelationDTO classProctoringTARelationDTO) {
        System.out.println("request received");
        int id = currentUserUtil.getCurrentUserId();
        return classProctoringTARelationService.updateClassProctoringDTO(classProctoringTARelationDTO, id);
    }

    /**
     * Retrieves all class proctorings and assigned TAs for the current user's department.
     *
     * @return a list of ClassProctoringAndTAsDTOs
     */
    @GetMapping("getDepartmentTAsClassProctorings")
    public List<ClassProctoringAndTAsDTO> getDepartmentTAsClassProctorings() {
        System.out.println("request received");
        int id = currentUserUtil.getCurrentUserId();
        return classProctoringAndTAs.getDepartmentTAsClassProctorings(id);
    }

    /**
     * Retrieves all class proctorings for a given department code.
     *
     * @param departmentCode the code of the department
     * @return a list of ClassProctoringAndTAsDTOs
     */
    @GetMapping("getDepartmentClassProctoringsByCode")
    public List<ClassProctoringAndTAsDTO> getDepartmentClassProctorings(@RequestParam String departmentCode) {
        System.out.println("Get department class proctorings");
        return classProctoringAndTAs.getDepartmentClassProctoringsByCode(departmentCode);
    }

    /**
     * Retrieves all class proctorings under a specified faculty.
     *
     * @param facultyId the ID of the faculty
     * @return a list of ClassProctoringAndTAsDTOs
     */
    @GetMapping("getFacultyClassProctoringsById")
    public List<ClassProctoringAndTAsDTO> getFacultyClassProctorings(@RequestParam int facultyId) {
        System.out.println("Get department class proctorings");
        return classProctoringAndTAs.getFacultyClassProctorings(facultyId);
    }

    /**
     * Removes a TA from a class proctoring.
     *
     * @param taId              the ID of the TA
     * @param classProctoringId the ID of the class proctoring
     * @return true if the TA was removed successfully, false otherwise
     */
    @DeleteMapping("removeTAFromClassProctoring")
    public boolean removeTAFromClassProctoring(@RequestParam int taId, @RequestParam int classProctoringId) {
        System.out.println("Remove ta from class proctoring");
        int removerId = currentUserUtil.getCurrentUserId();
        return classProctoringTARelationService.removeTAFromClassProctoring(taId, classProctoringId, removerId);
    }

    /**
     * Puts ta into a class proctoring.
     *
     * @param taId              the ID of the TA
     * @param classProctoringId the ID of the class proctoring
     * @return true if the relation was created successfully, false otherwise
     */
    @PostMapping("createClassProctoringTARelation")
    public boolean createClassProctoringTARelation(@RequestParam int taId, @RequestParam int classProctoringId) {
        System.out.println("Create classProctoringTARelation");
        return classProctoringTARelationService.createClassProctoringTARelation(taId, classProctoringId);
    }

    /**
     * Retrieves all class proctorings created by the currently logged-in user.
     *
     * @return a list of ClassProctoringAndTAsDTOs
     */
    @GetMapping("getClassProctoringOfCreator")
    public List<ClassProctoringAndTAsDTO> getClassProctoringOfCreator() {
        int creatorId = currentUserUtil.getCurrentUserId();
        System.out.println("getClassProctoringOfCreator called with creatorId = " + creatorId);
        return classProctoringAndTAs.getClassProctoringsOfCreator(creatorId);
    }

    /**
     * Retrieves all class proctorings where the currently logged-in user is the instructor.
     *
     * @return a list of ClassProctoringAndTAsDTOs
     */
    @GetMapping("getClassProctoringOfInstructor")
    public List<ClassProctoringAndTAsDTO> getClassProctoringOfInstructor() {
        int instructorId = currentUserUtil.getCurrentUserId();
        return classProctoringAndTAs.getClassProctoringsOfInstructor(instructorId);
    }

    @PostMapping("assignTAFromOtherFaculty")
    public boolean assignTAFromOtherFaculty(@RequestBody TAProfileDTO taProfileDTO, @RequestParam int classProctoringId) {
        return classProctoringTARelationService.assignTAFromOtherFaculty(taProfileDTO, classProctoringId);
    }

}