package com.cs319group3.backend.Controllers;


import com.cs319group3.backend.DTOs.ClassProctoringTARelationDTO;
import com.cs319group3.backend.Services.ClassProctoringTARelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/classProctoringTARelation")
@ComponentScan(basePackages = {"com.cs319group3.backend.Controllers"})
@CrossOrigin(origins = "http://localhost:3000")
public class ClassProctoringTARelationController {

    @Autowired
    public ClassProctoringTARelationService classProctoringTARelationService;


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
    public List<ClassProctoringTARelationDTO> getDepartmentTAsClassProctorings(@RequestParam("id") int id){
        System.out.println("request received");
        return classProctoringTARelationService.getDepartmentTAsClassProctorings(id);
    }
}
