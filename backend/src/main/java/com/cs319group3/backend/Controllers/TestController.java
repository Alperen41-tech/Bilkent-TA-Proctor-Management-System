package com.cs319group3.backend.Controllers;

import com.cs319group3.backend.Entities.ClassProctoring;
import com.cs319group3.backend.Entities.UserEntities.TA;
import com.cs319group3.backend.Repositories.ClassProctoringRepo;
import com.cs319group3.backend.Repositories.TARepo;
import com.cs319group3.backend.Services.TAAvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

    @Autowired
    TAAvailabilityService taAvailabilityService;

    @Autowired
    TARepo taRepo;

    @Autowired
    ClassProctoringRepo classProctoringRepo;

    @GetMapping("isAvailable")
    public boolean isTAAvailable(int taId, int classProctoringId){
        return taAvailabilityService.isTAAvailable(taRepo.findByUserId(taId).get(), classProctoringRepo.findById(classProctoringId).get());
    }
}
