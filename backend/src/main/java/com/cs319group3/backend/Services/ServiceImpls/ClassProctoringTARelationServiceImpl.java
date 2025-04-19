package com.cs319group3.backend.Services.ServiceImpls;

import com.cs319group3.backend.DTOMappers.ClassProctoringMapper;
import com.cs319group3.backend.DTOMappers.ClassProctoringTARelationMapper;
import com.cs319group3.backend.DTOs.ClassProctoringDTO;
import com.cs319group3.backend.DTOs.ClassProctoringTARelationDTO;

import com.cs319group3.backend.Entities.ClassProctoring;
import com.cs319group3.backend.Entities.RelationEntities.ClassProctoringTARelation;

import com.cs319group3.backend.Entities.UserEntities.TA;
import com.cs319group3.backend.Repositories.ClassProctoringRepo;
import com.cs319group3.backend.Repositories.ClassProctoringTARelationRepo;
import com.cs319group3.backend.Repositories.TARepo;
import com.cs319group3.backend.Services.ClassProctoringTARelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClassProctoringTARelationServiceImpl implements ClassProctoringTARelationService {

    @Autowired
    private ClassProctoringTARelationRepo classProctoringTARelationRepo;

    @Override
    public List<ClassProctoringTARelationDTO> getTAsClassProctoringDTOs(int id) {
        List<ClassProctoringTARelation> relations = classProctoringTARelationRepo.findById_TAId(id);

        List<ClassProctoringTARelationDTO> proctorings = new ArrayList<>();

        for (ClassProctoringTARelation relation : relations) {

            ClassProctoringTARelationDTO cdto = ClassProctoringTARelationMapper.essentialMapper(relation);
            proctorings.add(cdto);
        }
        return proctorings;
    }

    @Override
    public boolean updateClassProctoringDTO(ClassProctoringTARelationDTO dto, int userId) {
        // Step 1: Find the existing entity

        int classpId = dto.getClassProctoringDTO().getId();
        Optional<ClassProctoringTARelation> optionalRelation = classProctoringTARelationRepo.findById_ClassProctoringIdAndId_TAId(classpId, userId);

        if (optionalRelation.isEmpty()) {
            throw new RuntimeException("No ClassProctoringTARelation found with id " + classpId);
        }

        ClassProctoringTARelation relation = optionalRelation.get();

        // Step 2: Update fields
        relation.setOpenToSwap(dto.getIsOpenToSwap());
        // If you want to update more fields later, add them here
        // e.g., relation.setIsConfirmed(dto.isConfirmed());

        // Step 3: Save the updated entity
        classProctoringTARelationRepo.save(relation);
        return true;
    }

    @Autowired
    private TARepo taRepo;

    @Autowired
    private ClassProctoringRepo classProctoringRepo;

    @Override
    public List<ClassProctoringTARelationDTO> getDepartmentTAsClassProctorings(int userId){
        // Step 1: Find TA by user ID
        Optional<TA> ta = taRepo.findByUserId(userId);
        if (!ta.isPresent()) {
            throw new RuntimeException("No TA found with user ID " + userId);
        }

        int departmentId = ta.get().getDepartment().getDepartmentId();

        // Step 2: Find all class proctorings for this department
        List<ClassProctoring> classProctorings = classProctoringRepo.findByCourse_Department_DepartmentId(departmentId);

        // Step 3: Extract IDs of these proctorings
        List<Integer> classProctoringIds = classProctorings.stream()
                .map(ClassProctoring::getClassProctoringId) // or getClassProctoringId()
                .collect(Collectors.toList());

        // Step 4: Find all relations that point to these proctorings
        List<ClassProctoringTARelation> relations = classProctoringTARelationRepo.findByClassProctoring_ClassProctoringIdIn(classProctoringIds);

        // Step 5: Convert to DTOs
        List<ClassProctoringTARelationDTO> dtos = new ArrayList<>();
        for (ClassProctoringTARelation relation : relations) {
            dtos.add(ClassProctoringTARelationMapper.essentialMapper(relation));
        }

        return dtos;
    }
}
