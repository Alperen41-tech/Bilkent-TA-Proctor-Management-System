package com.cs319group3.backend.Services.ServiceImpls;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.cs319group3.backend.CompositeIDs.ClassProctoringTAKey;
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
import com.cs319group3.backend.Repositories.UserRepo;
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

    @Override
    public boolean removeTAFromClassProctoring(int taId, int classProctoringId) {
        Optional<ClassProctoringTARelation> classProctoringTARelation = classProctoringTARelationRepo.findById_ClassProctoringIdAndId_TAId(classProctoringId, taId);
        if (classProctoringTARelation.isPresent()) {
            classProctoringTARelationRepo.delete(classProctoringTARelation.get());
            return true;
        }
        return false;
    }

    @Autowired
    private ClassProctoringRepo classProctoringRepo;

    @Autowired
    private TARepo taRepo;

    @Override
    public boolean createClassProctoringTARelation(int taId, int classProctoringId) {
        int count = classProctoringTARelationRepo.countByClassProctoringId(classProctoringId);
        int taLimit = classProctoringRepo.findCountByClassProctoringId(classProctoringId);
        if(count >= taLimit) {
            return false;
        }
        Optional<ClassProctoringTARelation> classProctoringTARelation = classProctoringTARelationRepo.findById_ClassProctoringIdAndId_TAId(classProctoringId, taId);
        if (classProctoringTARelation.isPresent()) {
            return false;
        }
        // Fetch needed IDs first (cheap queries)
        Integer classProctoringDepartmentId = classProctoringRepo.findDepartmentIdByClassProctoringId(classProctoringId);
        Integer taDepartmentId = taRepo.findDepartmentIdByUserId(taId);

        if (classProctoringDepartmentId == null || taDepartmentId == null) {
            return false;
        }

        // Fetch full TA and ClassProctoring entities (now safe to assume they exist)
        Optional<TA> taOpt = taRepo.findByUserId(taId);
        Optional<ClassProctoring> cpOpt = classProctoringRepo.findById(classProctoringId);

        if (taOpt.isEmpty() || cpOpt.isEmpty()) {
            return false;
        }

        ClassProctoringTARelation relation = new ClassProctoringTARelation();
        relation.setOpenToSwap(true);
        relation.setComplete(false);
        relation.setTA(taOpt.get());
        relation.setClassProctoring(cpOpt.get());
        relation.setPaid(!taDepartmentId.equals(classProctoringDepartmentId));
        relation.setId(new ClassProctoringTAKey(classProctoringId, taId));

        classProctoringTARelationRepo.save(relation);

        return true;
    }
}
