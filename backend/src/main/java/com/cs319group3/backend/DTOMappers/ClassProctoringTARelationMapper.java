package com.cs319group3.backend.DTOMappers;

import com.cs319group3.backend.DTOs.ClassProctoringTARelationDTO;
import com.cs319group3.backend.Entities.RelationEntities.ClassProctoringTARelation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClassProctoringTARelationMapper {

    @Autowired
    ClassProctoringMapper classProctoringMapper;

    public ClassProctoringTARelationDTO essentialMapper(ClassProctoringTARelation classProctoringTARelation) {

        ClassProctoringTARelationDTO classProctoringTARelationDTO = new ClassProctoringTARelationDTO();

        classProctoringTARelationDTO.setClassProctoringDTO(classProctoringMapper.essentialMapper(classProctoringTARelation.getClassProctoring()));
        classProctoringTARelationDTO.setTAId(classProctoringTARelation.getTA().getUserId());

        classProctoringTARelationDTO.setPaid(classProctoringTARelation.isPaid());
        classProctoringTARelationDTO.setComplete(classProctoringTARelation.isComplete());
        classProctoringTARelationDTO.setIsOpenToSwap(classProctoringTARelation.isOpenToSwap());

        return classProctoringTARelationDTO;
    }

    public List<ClassProctoringTARelationDTO> essentialMapper(List<ClassProctoringTARelation> classProctoringTARelations) {

        List<ClassProctoringTARelationDTO> dtos = new ArrayList<>();

        for (ClassProctoringTARelation classProctoringTARelation : classProctoringTARelations) {
            dtos.add(essentialMapper(classProctoringTARelation));
        }
        return dtos;
    }






}
