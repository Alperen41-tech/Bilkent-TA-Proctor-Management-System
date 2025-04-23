package com.cs319group3.backend.Controllers;

import com.cs319group3.backend.DTOs.CreateClassProctoringDTO;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("classProctoring")
@CrossOrigin(origins = "http://localhost:3000")
public class ClassProctoringController {


    public boolean createClassProctoring(CreateClassProctoringDTO dto) {
        System.out.println("Creating class proctoring");
        return true;
    }
}
