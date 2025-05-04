package com.cs319group3.backend.Services.ServiceImpls;

import com.cs319group3.backend.DTOMappers.ClassProctoringMapper;
import com.cs319group3.backend.DTOMappers.CreateClassProctoringMapper;
import com.cs319group3.backend.DTOs.ClassProctoringDTO;
import com.cs319group3.backend.DTOs.CreateClassProctoringDTO;
import com.cs319group3.backend.Entities.ClassProctoring;
import com.cs319group3.backend.Entities.Log;
import com.cs319group3.backend.Enums.LogType;
import com.cs319group3.backend.Repositories.ClassProctoringRepo;
import com.cs319group3.backend.Repositories.LogRepo;
import com.cs319group3.backend.Services.ClassProctoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class ClassProctoringServiceImpl implements ClassProctoringService {

    @Autowired
    private LogRepo logRepo;

    @Override
    public List<ClassProctoringDTO> getClassProctoringList() {
        return List.of();
    }

    @Autowired
    CreateClassProctoringMapper createClassProctoringMapper;

    @Autowired
    ClassProctoringRepo classProctoringRepo;

    @Override
    public boolean createClassProctoring(CreateClassProctoringDTO dto){
        ClassProctoring classProctoring = createClassProctoringMapper.essentialEntityTo(dto);
        if(classProctoring == null){
            return false;
        }
        classProctoringRepo.save(classProctoring);
        String logMessage = "User " + classProctoring.getCreator().getUserId() + " created a class proctoring (" + classProctoring.getClassProctoringId() + ").";
        Log log = new Log();
        log.setMessage(logMessage);
        log.setLogType(LogType.CREATE);
        log.setLogDate(LocalDateTime.now());
        logRepo.save(log);
        return true;
    }

}
