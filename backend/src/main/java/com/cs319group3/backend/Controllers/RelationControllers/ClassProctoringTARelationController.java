package com.cs319group3.backend.Controllers.RelationControllers;


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
    public ClassProctoringTARelationService classProctoringTARelationService;
    @Autowired
    private ClassProctoringAndTAsService classProctoringAndTAs;


    @GetMapping("getTAsClassProctorings")
    public List<ClassProctoringTARelationDTO> getTAsClassProctorings(@RequestParam("id") int id){
        try {
            return classProctoringTARelationService.getTAsClassProctoringDTOs(id);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @GetMapping("getTAsClassProctoringsByDepartment")
    public List<ClassProctoringTARelationDTO> getTAsClassProctoringsByDepartment(@RequestParam("id") int id){
        try{
            return classProctoringTARelationService.getTAsClassProctoringsByDepartment(id);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PutMapping("updateTAsClassProctorings")
    public boolean updateTAsClassProctorings(@RequestBody ClassProctoringTARelationDTO classProctoringTARelationDTO, @RequestParam("id") int id ){
        System.out.println("request received");
        return classProctoringTARelationService.updateClassProctoringDTO(classProctoringTARelationDTO, id);
    }

    @GetMapping("getDepartmentTAsClassProctorings")
    public List<ClassProctoringAndTAsDTO> getDepartmentTAsClassProctorings(@RequestParam("id") int id){
        System.out.println("request received");
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
        return classProctoringTARelationService.removeTAFromClassProctoring(taId, classProctoringId);
    }

    @PostMapping("createClassProctoringTARelation")
    public boolean createClassProctoringTARelation(@RequestParam int taId, @RequestParam int classProctoringId){
        System.out.println("Create classProctoringTARelation");
        return classProctoringTARelationService.createClassProctoringTARelation(taId, classProctoringId);
    }

    @GetMapping("getClassProctoringOfCreator")
    public ResponseEntity<?> getClassProctoringOfCreator(@RequestParam int creatorId){
        System.out.println("getClassProctoringOfCreator called with creatorId = " + creatorId);
        try {
            List<ClassProctoringAndTAsDTO> result = classProctoringAndTAs.getClassProctoringsOfCreator(creatorId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace(); // 👈 THIS WILL SHOW THE ERROR IN BACKEND CONSOLE
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server Error: " + e.getMessage());
        }
    }

    @GetMapping("getClassProctoringOfInstructor")
    public List<ClassProctoringAndTAsDTO> getClassProctoringOfInstructor(@RequestParam int instructorId){
        return classProctoringAndTAs.getClassProctoringsOfInstructor(instructorId);
    }

}
