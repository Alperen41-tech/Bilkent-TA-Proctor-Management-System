package com.cs319group3.backend.Controllers.RequestControllers;

import com.cs319group3.backend.Entities.ClassProctoring;
import com.cs319group3.backend.Entities.RelationEntities.ClassProctoringTARelation;
import com.cs319group3.backend.Entities.UserEntities.TA;
import com.cs319group3.backend.Repositories.ClassProctoringTARelationRepo;
import com.cs319group3.backend.Services.AuthStaffProctoringRequestService;
import com.cs319group3.backend.Services.AuthenticationService;
import com.cs319group3.backend.Services.ClassProctoringTARelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("authStaffProctoringRequestController")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthStaffProctoringRequestController {

    @Autowired
    AuthStaffProctoringRequestService authStaffProctoringRequestService;

    @PostMapping("sendAuthStaffProctoringRequest")
    public boolean sendAuthStaffProctoringRequest(int classProctoringId, int taId, int senderId) {
        System.out.println("Class proctoring request sent.");
        return authStaffProctoringRequestService.sendAuthStaffProctoringRequest(classProctoringId, taId, senderId, false);
    }

    @Autowired
    ClassProctoringTARelationService classProctoringTARelation;

    @PostMapping("forceAuthStaffProctoringRequest")
    public boolean forceAuthStaffProctoringRequest(int classProctoringId, int taId, int senderId) {
        System.out.println("Force auth staff request sent.");
        if(authStaffProctoringRequestService.sendAuthStaffProctoringRequest(classProctoringId, taId, senderId, true)){
            boolean check = classProctoringTARelation.createClassProctoringTARelation(taId, classProctoringId);
            System.out.println("Burası " + check);
            return true;
        }
        return false;
    }
}
