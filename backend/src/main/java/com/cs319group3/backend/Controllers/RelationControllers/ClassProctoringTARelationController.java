package com.cs319group3.backend.Controllers.RelationControllers;


import com.cs319group3.backend.DTOs.ClassProctoringAndTAsDTO;
import com.cs319group3.backend.DTOs.ClassProctoringTARelationDTO;
import com.cs319group3.backend.Services.ClassProctoringAndTAsService;
import com.cs319group3.backend.Services.ClassProctoringTARelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/classProctoringTARelation")
@ComponentScan(basePackages = {"com.cs319group3.backend.Controllers.RelationControllers"})
@CrossOrigin(origins = "http://localhost:3000")
public class ClassProctoringTARelationController {

    @Autowired
    public ClassProctoringTARelationService classProctoringTARelationService;
    @Autowired
    private ClassProctoringAndTAsService classProctoringAndTAs;


    @GetMapping("getTAsClassProctorings")
    public List<ClassProctoringTARelationDTO> getTAsClassProctorings(@RequestParam("id") int id){
        System.out.println("request received");
        return classProctoringTARelationService.getTAsClassProctoringDTOs(id);
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

    @GetMapping("getDepartmentClassProctorings")
    public List<ClassProctoringAndTAsDTO> getDepartmentClassProctorings(@RequestParam int departmentId){
        System.out.println("Get department class proctorings");
        return classProctoringAndTAs.getDepartmentClassProctorings(departmentId);
    }

    @GetMapping("getFacultyClassProctorings")
    public List<ClassProctoringAndTAsDTO> getFacultyClassProctorings(@RequestParam int facultyId){
        System.out.println("Get department class proctorings");
        return classProctoringAndTAs.getFacultyClassProctorings(facultyId);
    }
}
