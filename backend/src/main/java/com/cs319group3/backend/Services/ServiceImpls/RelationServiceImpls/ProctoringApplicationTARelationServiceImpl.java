package com.cs319group3.backend.Services.ServiceImpls.RelationServiceImpls;


import com.cs319group3.backend.DTOMappers.TAProfileMapper;
import com.cs319group3.backend.DTOs.TAProfileDTO;
import com.cs319group3.backend.Entities.ProctoringApplication;
import com.cs319group3.backend.Entities.RelationEntities.ProctoringApplicationTARelation;
import com.cs319group3.backend.Entities.UserEntities.TA;
import com.cs319group3.backend.Repositories.ProctoringApplicationRepo;
import com.cs319group3.backend.Repositories.ProctoringApplicationTARelationRepo;
import com.cs319group3.backend.Repositories.TARepo;
import com.cs319group3.backend.Services.ProctoringApplicationTARelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProctoringApplicationTARelationServiceImpl implements ProctoringApplicationTARelationService {

    @Autowired
    private ProctoringApplicationTARelationRepo proctoringApplicationTARelationRepo;

    @Autowired
    private ProctoringApplicationRepo proctoringApplicationRepo;

    @Autowired
    private TARepo taRepo;

    @Autowired
    private TAProfileMapper taProfileMapper;

    @Override
    public ResponseEntity<Boolean> createProctoringApplicationTARelation(int proctoringApplicationId, int taId) {

        Optional<ProctoringApplicationTARelation> taRelation = proctoringApplicationTARelationRepo.findByTA_UserIdAndProctoringApplication_ApplicationId(taId, proctoringApplicationId);

        if (taRelation.isPresent()){
            throw new RuntimeException("already applied");
        }


        Optional<ProctoringApplication> proctoringApplication = proctoringApplicationRepo.findById(proctoringApplicationId);
        if (!proctoringApplication.isPresent()){
            throw new RuntimeException("proctoring application not found");
        }

        Optional<TA> ta = taRepo.findById(taId);
        if (!ta.isPresent()){
            throw new RuntimeException("ta not found");
        }


        ProctoringApplicationTARelation newApplication = new ProctoringApplicationTARelation();
        newApplication.setProctoringApplication(proctoringApplication.get());
        newApplication.setTA(ta.get());
        newApplication.setApprovedBySecretary(false);

        proctoringApplicationTARelationRepo.save(newApplication);

        return new ResponseEntity<>(true, HttpStatus.CREATED);
    }

    @Override
    public List<TAProfileDTO> getAllApplicants(int applicationId) {

        List<ProctoringApplicationTARelation> relations = proctoringApplicationTARelationRepo.findByProctoringApplication_ApplicationIdAndIsApprovedBySecretaryFalse(applicationId);
        List<TAProfileDTO> profiles = new ArrayList<>();

        relations.forEach(rel -> {
            profiles.add(taProfileMapper.essentialMapper(rel.getTA()));
        });

        return profiles;
    }

    @Override
    public int getApplicantCount(int applicationId) {
        List<ProctoringApplicationTARelation> relations = proctoringApplicationTARelationRepo.findByProctoringApplication_ApplicationId(applicationId);
        return relations.size();
    }


}
