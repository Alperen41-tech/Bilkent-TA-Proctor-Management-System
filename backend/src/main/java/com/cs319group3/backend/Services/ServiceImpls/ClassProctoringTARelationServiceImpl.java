package com.cs319group3.backend.Services.ServiceImpls;

import com.cs319group3.backend.DTOMappers.ClassProctoringMapper;
import com.cs319group3.backend.DTOs.ClassProctoringDTO;
import com.cs319group3.backend.Entities.ClassProctoring;
import com.cs319group3.backend.Entities.RelationEntities.ClassProctoringTARelation;

import com.cs319group3.backend.Repositories.ClassProctoringTARelationRepo;
import com.cs319group3.backend.Services.ClassProctoringTARelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClassProctoringTARelationServiceImpl implements ClassProctoringTARelationService {

    @Autowired
    private ClassProctoringTARelationRepo classProctoringTARelationRepo;

    @Override
    public List<ClassProctoringDTO> getTAsClassProctoringDTOs(int id) {
        List<ClassProctoringTARelation> relations = classProctoringTARelationRepo.findById_TAId(id);

        List<ClassProctoringDTO> proctorings = new ArrayList<>();

        for (ClassProctoringTARelation relation : relations) {


            ClassProctoring proctoring = relation.getClassProctoring();
            ClassProctoringDTO dto = ClassProctoringMapper.essentialMapper(proctoring);

            proctorings.add(dto);

        }
        return proctorings;
    }
}
