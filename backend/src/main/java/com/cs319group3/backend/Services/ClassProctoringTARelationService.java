package com.cs319group3.backend.Services;


import com.cs319group3.backend.DTOs.ClassProctoringDTO;
import com.cs319group3.backend.DTOs.ClassProctoringTARelationDTO;
import com.cs319group3.backend.Entities.RelationEntities.ClassProctoringTARelation;


import java.util.List;

public interface ClassProctoringTARelationService {



    public List<ClassProctoringTARelationDTO> getTAsClassProctoringDTOs(int id) throws Exception;
    public boolean updateClassProctoringDTO(ClassProctoringTARelationDTO dto, int userId);
    public boolean removeTAFromClassProctoring(int taId, int classProctoringId);
    public boolean createClassProctoringTARelation(int taId, int classProctoringId);
    public List<ClassProctoringTARelationDTO> getTAsClassProctoringsByDepartment(int taId) throws Exception;

}
