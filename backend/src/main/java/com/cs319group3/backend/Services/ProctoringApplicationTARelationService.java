package com.cs319group3.backend.Services;


import com.cs319group3.backend.DTOs.ProctoringApplicationDTO;
import com.cs319group3.backend.DTOs.TAProfileDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProctoringApplicationTARelationService {


    ResponseEntity<Boolean> createProctoringApplicationTARelation(int proctoringApplicationId, int taId);

    List<TAProfileDTO> getAllApplicants(int applicationId);

    int getApplicantCount(int applicationId);

    boolean deleteProctoringApplicationTARelation(int clasProctoringId, TAProfileDTO applicant);

}
