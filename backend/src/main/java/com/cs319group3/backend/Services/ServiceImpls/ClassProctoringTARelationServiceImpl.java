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
import com.cs319group3.backend.Entities.UserEntities.User;
import com.cs319group3.backend.Enums.NotificationType;
import com.cs319group3.backend.Repositories.*;
import com.cs319group3.backend.Services.ClassProctoringTARelationService;
import com.cs319group3.backend.Services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.cs319group3.backend.Enums.NotificationType.DISMISS;

@Service
public class ClassProctoringTARelationServiceImpl implements ClassProctoringTARelationService {

    @Autowired
    private ClassProctoringTARelationRepo classProctoringTARelationRepo;

    @Autowired
    private TARepo taRepo;

    @Autowired
    private ClassProctoringRepo classProctoringRepo;
    @Autowired
    private NotificationRepo notificationRepo;


    @Override
    public List<ClassProctoringTARelationDTO> getTAsClassProctoringDTOs(int taId) throws Exception{

        Optional<TA> ta = taRepo.findByUserId(taId);
        if (!ta.isPresent()) {
            throw new Exception("No such ta");
        }

        List<ClassProctoringTARelation> relations = classProctoringTARelationRepo.findById_TAId(taId);
        return ClassProctoringTARelationMapper.essentialMapper(relations);
    }

    @Override
    public List<ClassProctoringTARelationDTO> getTAsClassProctoringsByDepartment(int taId) throws Exception{

        Optional<TA> ta = taRepo.findByUserId(taId);
        if (!ta.isPresent()) {
            throw new Exception("No such ta");
        }

        List<ClassProctoringTARelation> relations = classProctoringTARelationRepo
                .findByClassProctoring_Course_Department_DepartmentIdAndTA_UserId(ta.get().getUserId(), taId);

        return ClassProctoringTARelationMapper.essentialMapper(relations);


    }

    @Override
    public boolean updateClassProctoringDTO(ClassProctoringTARelationDTO dto, int userId) {
        // Step 1: Find the existing entity

        int classId = dto.getClassProctoringDTO().getId();
        Optional<ClassProctoringTARelation> optionalRelation = classProctoringTARelationRepo.findById_ClassProctoringIdAndId_TAId(classId, userId);

        if (optionalRelation.isEmpty()) {
            throw new RuntimeException("No ClassProctoringTARelation found with id " + classId);
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
    NotificationService notificationService;

    @Autowired
    UserRepo userRepo;

    @Override
    public boolean removeTAFromClassProctoring(int taId, int classProctoringId, int removerId) {
        Optional<ClassProctoringTARelation> classProctoringTARelation = classProctoringTARelationRepo.findById_ClassProctoringIdAndId_TAId(classProctoringId, taId);
        if (classProctoringTARelation.isPresent()) {
            Optional<User> remover = userRepo.findByUserId(removerId);
            if(remover.isEmpty()) {
                throw new RuntimeException("Could not find the remover of this ta-proctoring: " + removerId);
            }
            classProctoringTARelationRepo.delete(classProctoringTARelation.get());
            String description = "You are dismissed by "+remover.get().getName()+ " from the class proctoring.";
            notificationService.createNotificationWithoutRequest(DISMISS, remover.get(), description);
            return true;
        }
        return false;
    }

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
