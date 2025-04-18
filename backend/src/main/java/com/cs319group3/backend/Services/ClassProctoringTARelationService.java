package com.cs319group3.backend.Services;


import com.cs319group3.backend.DTOs.ClassProctoringDTO;
import com.cs319group3.backend.DTOs.ClassProctoringTARelationDTO;


import java.util.List;

public interface ClassProctoringTARelationService {






    public List<ClassProctoringDTO> getTAsClassProctoringDTOs(int id);
    public boolean updateClassProctoringDTO(ClassProctoringTARelationDTO dto, int userId);



}
