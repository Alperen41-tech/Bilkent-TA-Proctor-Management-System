package com.cs319group3.backend.Services.ServiceImpls;

import com.cs319group3.backend.DTOMappers.ClassProctoringMapper;
import com.cs319group3.backend.DTOMappers.CreateClassProctoringMapper;
import com.cs319group3.backend.DTOs.ClassProctoringDTO;
import com.cs319group3.backend.DTOs.CreateClassProctoringDTO;
import com.cs319group3.backend.Entities.ClassProctoring;
import com.cs319group3.backend.Entities.RequestEntities.AuthStaffProctoringRequest;
import com.cs319group3.backend.Enums.LogType;
import com.cs319group3.backend.Repositories.AuthStaffProctoringRequestRepo;
import com.cs319group3.backend.Repositories.ClassProctoringRepo;
import com.cs319group3.backend.Repositories.ClassProctoringTARelationRepo;
import com.cs319group3.backend.Services.ClassProctoringService;
import com.cs319group3.backend.Services.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ClassProctoringServiceImpl implements ClassProctoringService {

    @Autowired
    private LogService logService;

    @Autowired
    private CreateClassProctoringMapper createClassProctoringMapper;

    @Autowired
    private ClassProctoringRepo classProctoringRepo;

    @Autowired
    private AuthStaffProctoringRequestRepo authStaffProctoringRequestRepo;

    @Autowired
    private ClassProctoringTARelationRepo classProctoringTARelationRepo;

    @Autowired
    private ClassProctoringMapper classProctoringMapper;

    @Override
    public List<ClassProctoringDTO> getClassProctoringList() {
        return List.of();
    }

    @Override
    public boolean createClassProctoring(CreateClassProctoringDTO dto) {
        ClassProctoring classProctoring = createClassProctoringMapper.essentialEntityTo(dto);
        if (classProctoring == null) {
            return false;
        }

        classProctoringRepo.save(classProctoring);

        String logMessage = "User " + classProctoring.getCreator().getUserId()
                + " created a class proctoring (" + classProctoring.getClassProctoringId() + ").";
        logService.createLog(logMessage, LogType.CREATE);

        return true;
    }

    @Override
    public int numberOfRequestsSent(int classProctoringId) {
        List<AuthStaffProctoringRequest> requestList = authStaffProctoringRequestRepo.findByClassProctoringClassProctoringIdAndResponseDateIsNullAndApprovedFalse(classProctoringId);
        Set<String> uniquePairs = new HashSet<>();

        for (AuthStaffProctoringRequest request : requestList) {
            int receiverId = request.getReceiverUser().getUserId();
            int proctoringId = request.getClassProctoring().getClassProctoringId();

            String key = receiverId + "-" + proctoringId;

            uniquePairs.add(key);
        }
        return uniquePairs.size();
    }

    @Override
    public int numberOfTAsAssigned(int classProctoringId) {
        return classProctoringTARelationRepo.countAssignedTAs(classProctoringId);
    }

    @Override
    public List<ClassProctoringDTO> getAllClassProctoring() {
        List<ClassProctoring> classProctoringList = classProctoringRepo.findAll();
        List<ClassProctoringDTO> classProctoringDTOList = new ArrayList<>();

        for (ClassProctoring classProctoring : classProctoringList) {
            classProctoringDTOList.add(classProctoringMapper.essentialMapper(classProctoring));
        }

        return classProctoringDTOList;
    }
}