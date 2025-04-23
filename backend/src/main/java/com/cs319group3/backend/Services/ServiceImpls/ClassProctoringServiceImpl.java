package com.cs319group3.backend.Services.ServiceImpls;

import com.cs319group3.backend.DTOMappers.ClassProctoringMapper;
import com.cs319group3.backend.DTOs.ClassProctoringDTO;
import com.cs319group3.backend.DTOs.CreateClassProctoringDTO;
import com.cs319group3.backend.Entities.ClassProctoring;
import com.cs319group3.backend.Repositories.ClassProctoringRepo;
import com.cs319group3.backend.Services.ClassProctoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ClassProctoringServiceImpl implements ClassProctoringService {

    @Override
    public List<ClassProctoringDTO> getClassProctoringList() {
        return List.of();
    }

    @Autowired
    ClassProctoringMapper classProctoringMapper;
    @Autowired
    ClassProctoringRepo classProctoringRepo;
    @Override
    public boolean createClassProctoring(CreateClassProctoringDTO dto){
        ClassProctoring classProctoring = classProctoringMapper.essentialEntityTo(dto);
        if(classProctoring == null){
            return false;
        }
        classProctoringRepo.save(classProctoring);
        return true;
    }
}
