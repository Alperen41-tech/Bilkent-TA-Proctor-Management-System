package com.cs319group3.backend.DTOMappers;

import com.cs319group3.backend.DTOs.ProctoringApplicationDTO;
import com.cs319group3.backend.Entities.ProctoringApplication;
import com.cs319group3.backend.Services.ProctoringApplicationTARelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProctoringApplicationMapper {

    @Autowired
    private ProctoringApplicationTARelationService proctoringApplicationTARelationService;


    public ProctoringApplicationDTO toDTO(ProctoringApplication proctoringApplication) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        ProctoringApplicationDTO proctoringApplicationDTO = new ProctoringApplicationDTO();
        proctoringApplicationDTO.setApplicationId(proctoringApplication.getApplicationId());
        proctoringApplicationDTO.setApplicantCountLimit(proctoringApplication.getApplicantCountLimit());
        proctoringApplicationDTO.setFinishDate(proctoringApplication.getFinishDate().format(formatter));
        proctoringApplicationDTO.setVisibleForTAs(proctoringApplication.isVisibleForTAs());
        proctoringApplicationDTO.setVisibleDepartmentId(proctoringApplication.getVisibleDepartment().getDepartmentId());
        proctoringApplicationDTO.setComplete(proctoringApplication.isComplete());
        proctoringApplicationDTO.setClassProctoringDTO(ClassProctoringMapper.essentialMapper(proctoringApplication.getClassProctoring()));

        if (proctoringApplicationDTO.getApplicationId() != 0){
            proctoringApplicationDTO
                    .setApplicantCount(proctoringApplicationTARelationService.getApplicantCount(proctoringApplication.getApplicationId()));
        }

        return proctoringApplicationDTO;
    }

    public List<ProctoringApplicationDTO> toDTO(List<ProctoringApplication> proctoringApplications) {
        List<ProctoringApplicationDTO> proctoringApplicationDTOs = new ArrayList<>();
        for (ProctoringApplication proctoringApplication : proctoringApplications) {
            proctoringApplicationDTOs.add(toDTO(proctoringApplication));
        }
        return proctoringApplicationDTOs;
    }
}
