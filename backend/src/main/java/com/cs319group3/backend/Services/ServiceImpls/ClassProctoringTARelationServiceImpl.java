package com.cs319group3.backend.Services.ServiceImpls;

import com.cs319group3.backend.CompositeIDs.ClassProctoringTAKey;
import com.cs319group3.backend.DTOMappers.ClassProctoringTARelationMapper;
import com.cs319group3.backend.DTOs.ClassProctoringTARelationDTO;
import com.cs319group3.backend.DTOs.TAProfileDTO;
import com.cs319group3.backend.Entities.ClassProctoring;
import com.cs319group3.backend.Entities.RelationEntities.ClassProctoringTARelation;
import com.cs319group3.backend.Entities.RequestEntities.TASwapRequest;
import com.cs319group3.backend.Entities.UserEntities.TA;
import com.cs319group3.backend.Entities.UserEntities.User;
import com.cs319group3.backend.Enums.LogType;
import com.cs319group3.backend.Repositories.*;
import com.cs319group3.backend.Services.ClassProctoringTARelationService;
import com.cs319group3.backend.Services.LogService;
import com.cs319group3.backend.Services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

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
    private LogService logService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ClassProctoringTARelationMapper classProctoringTARelationMapper;

    @Autowired
    private TASwapRequestRepo taswapRequestRepo;

    @Override
    public List<ClassProctoringTARelationDTO> getTAsClassProctoringDTOs(int taId) throws Exception {
        Optional<TA> ta = taRepo.findByUserId(taId);
        if (ta.isEmpty()) {
            throw new Exception("No such ta");
        }
        List<ClassProctoringTARelation> relations = classProctoringTARelationRepo.findById_TAId(taId);
        return classProctoringTARelationMapper.essentialMapper(relations);
    }

    @Override
    public List<ClassProctoringTARelationDTO> getTAsClassProctoringsByDepartment(int taId) throws Exception {
        Optional<TA> ta = taRepo.findByUserId(taId);
        if (ta.isEmpty()) {
            throw new Exception("No such ta");
        }
        List<ClassProctoringTARelation> relations = classProctoringTARelationRepo
                .findByClassProctoring_Course_Department_DepartmentIdAndTA_UserId(
                        ta.get().getDepartment().getDepartmentId(), taId);

        List<ClassProctoringTARelationDTO> classProctoringTARelationDTOs = classProctoringTARelationMapper.essentialMapper(relations);

        for (ClassProctoringTARelationDTO relation : classProctoringTARelationDTOs) {

            Optional<TASwapRequest> req = taswapRequestRepo.findBySenderUser_UserIdAndClassProctoring_ClassProctoringId(taId, relation.getClassProctoringDTO().getId());
            if (req.isEmpty()) {
                relation.setSwapRequestable(true);
            }
        }

        return classProctoringTARelationDTOs;
    }

    @Override
    public boolean assignTAFromOtherFaculty(TAProfileDTO dto, int classProctoringId) {
        Optional<TA> optionalTA = taRepo.findByBilkentId(dto.getBilkentId());
        if (optionalTA.isPresent() && !optionalTA.get().getEmail().equals(dto.getEmail())) {
            throw new RuntimeException("TA with bilkentId" + dto.getBilkentId() + "exists with a different email");
        }

        TA ta;
        if (!optionalTA.isPresent()) {
            ta = new TA();
            ta.setBilkentId(dto.getBilkentId());
            ta.setEmail(dto.getEmail());
            ta.setName(dto.getName());
            ta.setSurname(dto.getSurname());
            taRepo.save(ta);
        }
        else
            ta = optionalTA.get();

        return createClassProctoringTARelation(ta.getUserId(), classProctoringId);
    }

    @Override
    public boolean updateClassProctoringDTO(ClassProctoringTARelationDTO dto, int userId) {
        int classId = dto.getClassProctoringDTO().getId();
        Optional<ClassProctoringTARelation> optionalRelation =
                classProctoringTARelationRepo.findById_ClassProctoringIdAndId_TAId(classId, userId);

        if (optionalRelation.isEmpty()) {
            throw new RuntimeException("No ClassProctoringTARelation found with id " + classId);
        }

        ClassProctoringTARelation relation = optionalRelation.get();
        relation.setOpenToSwap(dto.getIsOpenToSwap());
        classProctoringTARelationRepo.save(relation);
        return true;
    }

    @Override
    public boolean removeTAFromClassProctoring(int taId, int classProctoringId, int removerId) {
        Optional<ClassProctoringTARelation> relation =
                classProctoringTARelationRepo.findById_ClassProctoringIdAndId_TAId(classProctoringId, taId);

        if (relation.isPresent()) {
            Optional<User> remover = userRepo.findByUserId(removerId);
            if (remover.isEmpty()) {
                throw new RuntimeException("Could not find the remover of this ta-proctoring: " + removerId);
            }
            classProctoringTARelationRepo.delete(relation.get());
            String description = "You are dismissed by " + remover.get().getName() + " from the class proctoring.";
            long minutes = ChronoUnit.MINUTES.between(relation.get().getClassProctoring().getStartDate(), relation.get().getClassProctoring().getEndDate());
            TA ta = relation.get().getTA();
            ta.setWorkload(ta.getWorkload() - (int) minutes);
            taRepo.save(ta);
            notificationService.createNotificationWithoutRequest(DISMISS, remover.get(), description);
            return true;
        }

        return false;
    }

    @Override
    public boolean createClassProctoringTARelation(int taId, int classProctoringId) {
        System.out.println("Creating ClassProctoringTARelation for taId: " + taId + " classProctoringId: " + classProctoringId);

        int count = classProctoringTARelationRepo.countByClassProctoringId(classProctoringId);
        int taLimit = classProctoringRepo.findCountByClassProctoringId(classProctoringId);

        if (count >= taLimit) {
            System.out.println("Class proctoring is full");
            return false;
        }

        Optional<ClassProctoringTARelation> existing =
                classProctoringTARelationRepo.findById_ClassProctoringIdAndId_TAId(classProctoringId, taId);

        if (existing.isPresent()) {
            System.out.println("No such ClassProctoringTARelation found");
            return false;
        }

        Integer classProctoringDepartmentId = classProctoringRepo.findDepartmentIdByClassProctoringId(classProctoringId);
        Integer taDepartmentId = taRepo.findDepartmentIdByUserId(taId);

        boolean isPaid;
        if (taDepartmentId == null)
            isPaid = true;
        else
            isPaid = !taDepartmentId.equals(classProctoringDepartmentId);

        Optional<TA> taOpt = taRepo.findByUserId(taId);
        Optional<ClassProctoring> cpOpt = classProctoringRepo.findById(classProctoringId);

        if (taOpt.isEmpty() || cpOpt.isEmpty()) {
            System.out.println("TA or ClassProctoring not found");
            return false;
        }

        ClassProctoringTARelation relation = new ClassProctoringTARelation();
        relation.setOpenToSwap(true);
        relation.setComplete(false);
        relation.setTA(taOpt.get());
        relation.setClassProctoring(cpOpt.get());
        relation.setPaid(isPaid);
        relation.setId(new ClassProctoringTAKey(classProctoringId, taId));

        String logMessage = "TA " + taId + " is assigned to class proctoring " + classProctoringId + ".";
        logService.createLog(logMessage, LogType.CREATE);
        classProctoringTARelationRepo.save(relation);

        return true;
    }
}