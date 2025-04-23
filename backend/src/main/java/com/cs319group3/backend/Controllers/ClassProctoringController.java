package com.cs319group3.backend.Controllers;

import com.cs319group3.backend.DTOs.CreateClassProctoringDTO;
import com.cs319group3.backend.Services.ClassProctoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("classProctoring")
@CrossOrigin(origins = "http://localhost:3000")
public class ClassProctoringController {

    @Autowired
    private ClassProctoringService classProctoringService;

    @PostMapping("createClassProctoring")
    public boolean createClassProctoring(@RequestBody CreateClassProctoringDTO dto) {
        System.out.println("Creating class proctoring");
        return classProctoringService.createClassProctoring(dto);
    }
}
