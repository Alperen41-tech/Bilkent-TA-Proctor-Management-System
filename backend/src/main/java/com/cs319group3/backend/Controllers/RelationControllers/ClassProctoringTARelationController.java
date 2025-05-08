package com.cs319group3.backend.Controllers.RelationControllers;


import com.cs319group3.backend.Components.CurrentUserUtil;
import com.cs319group3.backend.DTOs.ClassProctoringAndTAsDTO;
import com.cs319group3.backend.DTOs.ClassProctoringTARelationDTO;
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


    @GetMapping("getTAsClassProctorings")
    public List<ClassProctoringTARelationDTO> getTAsClassProctorings(){
        try {
            int id = currentUserUtil.getCurrentUserId();
            return classProctoringTARelationService.getTAsClassProctoringDTOs(id);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @GetMapping("getTAsClassProctoringsByDepartment")
    public List<ClassProctoringTARelationDTO> getTAsClassProctoringsByDepartment(){
        try{
            int userId = currentUserUtil.getCurrentUserId();
            return classProctoringTARelationService.getTAsClassProctoringsByDepartment(userId);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PutMapping("updateTAsClassProctorings")
    public boolean updateTAsClassProctorings(@RequestBody ClassProctoringTARelationDTO classProctoringTARelationDTO){
        System.out.println("request received");
        int id = currentUserUtil.getCurrentUserId();
        return classProctoringTARelationService.updateClassProctoringDTO(classProctoringTARelationDTO, id);
    }

    @GetMapping("getDepartmentTAsClassProctorings")
    public List<ClassProctoringAndTAsDTO> getDepartmentTAsClassProctorings(){
        System.out.println("request received");
        int id = currentUserUtil.getCurrentUserId();
        return classProctoringAndTAs.getDepartmentTAsClassProctorings(id);
    }

    @GetMapping("getDepartmentClassProctoringsByCode")
    public List<ClassProctoringAndTAsDTO> getDepartmentClassProctorings(@RequestParam String departmentCode){
        System.out.println("Get department class proctorings");
        return classProctoringAndTAs.getDepartmentClassProctoringsByCode(departmentCode);
    }

    @GetMapping("getFacultyClassProctoringsById")
    public List<ClassProctoringAndTAsDTO> getFacultyClassProctorings(@RequestParam int facultyId){
        System.out.println("Get department class proctorings");
        return classProctoringAndTAs.getFacultyClassProctorings(facultyId);
    }

    @DeleteMapping("removeTAFromClassProctoring")
    public boolean removeTAFromClassProctoring(@RequestParam int taId, @RequestParam int classProctoringId){
        System.out.println("Remove ta from class proctoring");
        int removerId = currentUserUtil.getCurrentUserId();
        return classProctoringTARelationService.removeTAFromClassProctoring(taId, classProctoringId, removerId);
    }

    @PostMapping("createClassProctoringTARelation")
    public boolean createClassProctoringTARelation(@RequestParam int taId, @RequestParam int classProctoringId){
        System.out.println("Create classProctoringTARelation");
        return classProctoringTARelationService.createClassProctoringTARelation(taId, classProctoringId);
    }

    @GetMapping("getClassProctoringOfCreator")
    public List<ClassProctoringAndTAsDTO> getClassProctoringOfCreator(){
        int creatorId = currentUserUtil.getCurrentUserId();
        System.out.println("getClassProctoringOfCreator called with creatorId = " + creatorId);
        return classProctoringAndTAs.getClassProctoringsOfCreator(creatorId);
    }

    @GetMapping("getClassProctoringOfInstructor")
    public List<ClassProctoringAndTAsDTO> getClassProctoringOfInstructor(){
        int instructorId = currentUserUtil.getCurrentUserId();
        return classProctoringAndTAs.getClassProctoringsOfInstructor(instructorId);
    }

}
