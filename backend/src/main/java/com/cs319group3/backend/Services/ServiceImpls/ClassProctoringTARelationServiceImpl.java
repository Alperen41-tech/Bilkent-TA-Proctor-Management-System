package com.cs319group3.backend.Services.ServiceImpls;

import com.cs319group3.backend.DTOMappers.ClassProctoringMapper;
import com.cs319group3.backend.DTOs.ClassProctoringDTO;
import com.cs319group3.backend.Entities.ClassProctoring;
import com.cs319group3.backend.Entities.RelationEntities.ClassProctoringTARelation;

import com.cs319group3.backend.Repositories.ClassProctoringTARelationRepo;
import com.cs319group3.backend.Services.ClassProctoringTARelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClassProctoringTARelationServiceImpl implements ClassProctoringTARelationService {

    @Autowired
    private ClassProctoringTARelationRepo classProctoringTARelationRepo;

    @Override
    public List<ClassProctoringDTO> getTAsClassProctoringDTOs(int id) {
        List<ClassProctoringTARelation> relations = classProctoringTARelationRepo.findById_TAId(id);

        List<ClassProctoringDTO> proctorings = new ArrayList<>();

        for (ClassProctoringTARelation relation : relations) {


            ClassProctoring proctoring = relation.getClassProctoring();
            ClassProctoringDTO dto = ClassProctoringMapper.essentialMapper(proctoring);

            proctorings.add(dto);

        }
        return proctorings;
    }

    @Override
    public void updateClassProctoringDTO(ClassProctoringDTO dto, int userId) {
        // Step 1: Find the existing entity
        Optional<ClassProctoringTARelation> optionalRelation = classProctoringTARelationRepo.findById_ClassProctoringIdAndId_TAId(dto.getId(), userId);

        if (optionalRelation.isEmpty()) {
            throw new RuntimeException("No ClassProctoringTARelation found with id " + dto.getId());
        }

        ClassProctoringTARelation relation = optionalRelation.get();

        // Step 2: Update fields
        relation.setOpenToSwap(dto.isOpenToSwap());
        // If you want to update more fields later, add them here
        // e.g., relation.setIsConfirmed(dto.isConfirmed());

        // Step 3: Save the updated entity
        classProctoringTARelationRepo.save(relation);
    }
}
