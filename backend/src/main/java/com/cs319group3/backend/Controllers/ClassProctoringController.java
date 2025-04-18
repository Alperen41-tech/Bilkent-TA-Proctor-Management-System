package com.cs319group3.backend.Controllers;


import com.cs319group3.backend.DTOs.ClassProctoringDTO;
import com.cs319group3.backend.DTOs.ClassProctoringTARelationDTO;
import com.cs319group3.backend.Services.ClassProctoringTARelationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/classProctoring")
@ComponentScan(basePackages = {"com.cs319group3.backend.Controllers"})
@CrossOrigin(origins = "http://localhost:3000")
public class ClassProctoringController {

    @Autowired
    public ClassProctoringTARelationService classProctoringTARelationService;


    @GetMapping("getTAsClassProctorings")
    public List<ClassProctoringDTO> getTAsClassProctorings(@RequestParam("id") int id){
        System.out.println("request received");
        return classProctoringTARelationService.getTAsClassProctoringDTOs(id);
    }

    @PutMapping("updateTAsClassProctorings")
    public boolean updateTAsClassProctorings(@RequestBody ClassProctoringTARelationDTO classProctoringTARelationDTO, @RequestParam("id") int id ){
        System.out.println("request received");
        return classProctoringTARelationService.updateClassProctoringDTO(classProctoringTARelationDTO, id);
    }


}
