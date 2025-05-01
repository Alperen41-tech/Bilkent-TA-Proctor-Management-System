package com.cs319group3.backend.DTOMappers;

import com.cs319group3.backend.DTOs.ProctoringApplicationDTO;
import com.cs319group3.backend.Entities.ProctoringApplication;

import java.time.format.DateTimeFormatter;

public class ProctoringApplicationMapper {
    public static ProctoringApplicationDTO toDTO(ProctoringApplication proctoringApplication) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        ProctoringApplicationDTO proctoringApplicationDTO = new ProctoringApplicationDTO();
        proctoringApplicationDTO.setApplicationId(proctoringApplication.getApplicationId());
        proctoringApplicationDTO.setApplicantCountLimit(proctoringApplication.getApplicantCountLimit());
        proctoringApplicationDTO.setFinishDate(proctoringApplication.getFinishDate().format(formatter));
        proctoringApplicationDTO.setVisibleForTAs(proctoringApplication.isVisibleForTAs());
        proctoringApplicationDTO.setVisibleDepartmentId(proctoringApplication.getVisibleDepartment().getDepartmentId());
        proctoringApplicationDTO.setComplete(proctoringApplication.isComplete());
        proctoringApplicationDTO.setClassProctoringDTO(ClassProctoringMapper.essentialMapper(proctoringApplication.getClassProctoring()));
        return proctoringApplicationDTO;
    }
}
