package com.cs319group3.backend.Controllers;


import com.cs319group3.backend.DTOs.TAsClassProctoringDTO;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/classProctoring")
@AllArgsConstructor
@ComponentScan(basePackages = {"com.cs319group3.backend.Controllers"})
@CrossOrigin(origins = "http://localhost:3000")
public class ClassProctoringController {




    @GetMapping("getTAsClassProctorings")
    public List<TAsClassProctoringDTO> getTAsClassProctorings(@RequestParam int id){



    }











}
