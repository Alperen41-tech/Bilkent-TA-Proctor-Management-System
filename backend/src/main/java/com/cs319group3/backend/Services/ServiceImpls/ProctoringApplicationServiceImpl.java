package com.cs319group3.backend.Services.ServiceImpls;

import com.cs319group3.backend.Controllers.ProctoringApplicationController;
import com.cs319group3.backend.DTOMappers.ClassProctoringMapper;
import com.cs319group3.backend.DTOMappers.ProctoringApplicationMapper;
import com.cs319group3.backend.DTOs.ProctoringApplicationDTO;
import com.cs319group3.backend.Entities.ClassProctoring;
import com.cs319group3.backend.Entities.ProctoringApplication;
import com.cs319group3.backend.Repositories.ClassProctoringRepo;
import com.cs319group3.backend.Repositories.ProctoringApplicationRepo;
import com.cs319group3.backend.Services.ProctoringApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProctoringApplicationServiceImpl implements ProctoringApplicationService {
    @Autowired
    ProctoringApplicationRepo proctoringApplicationRepo;
    @Override
    public List<ProctoringApplicationDTO> getProctoringApplications(int deansOfficeId){
        List<ProctoringApplication> listPA = proctoringApplicationRepo.findByDeansOfficeId(deansOfficeId);
        List<ProctoringApplicationDTO> dtoPA = new ArrayList<>();
        for(ProctoringApplication proctoringApplication:listPA){
            ProctoringApplicationDTO dto = ProctoringApplicationMapper.toDTO(proctoringApplication);
            dto.setClassProctoringDTO(ClassProctoringMapper.essentialMapper(proctoringApplication.getClassProctoring()));
            dtoPA.add(dto);
        }
        return dtoPA;
    }
}
