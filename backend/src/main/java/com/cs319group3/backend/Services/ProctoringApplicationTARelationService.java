package com.cs319group3.backend.Services;


import com.cs319group3.backend.DTOs.TAProfileDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProctoringApplicationTARelationService {


    public ResponseEntity<Boolean> createProctoringApplicationTARelation(int proctoringApplicationId, int taId);

    public List<TAProfileDTO> getAllApplicants(int applicationId);
}
