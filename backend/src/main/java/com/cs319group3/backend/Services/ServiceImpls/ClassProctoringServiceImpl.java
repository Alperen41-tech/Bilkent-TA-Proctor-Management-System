package com.cs319group3.backend.Services.ServiceImpls;

import com.cs319group3.backend.DTOMappers.ClassProctoringMapper;
import com.cs319group3.backend.DTOMappers.CreateClassProctoringMapper;
import com.cs319group3.backend.DTOs.ClassProctoringDTO;
import com.cs319group3.backend.DTOs.CreateClassProctoringDTO;
import com.cs319group3.backend.Entities.ClassProctoring;
import com.cs319group3.backend.Enums.LogType;
import com.cs319group3.backend.Repositories.AuthStaffProctoringRequestRepo;
import com.cs319group3.backend.Repositories.ClassProctoringRepo;
import com.cs319group3.backend.Repositories.ClassProctoringTARelationRepo;
import com.cs319group3.backend.Services.ClassProctoringService;
import com.cs319group3.backend.Services.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        return authStaffProctoringRequestRepo.numberOfRequestsSent(classProctoringId);
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
            classProctoringDTOList.add(ClassProctoringMapper.essentialMapper(classProctoring));
        }

        return classProctoringDTOList;
    }
}