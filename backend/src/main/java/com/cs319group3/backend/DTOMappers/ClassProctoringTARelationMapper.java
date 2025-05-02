package com.cs319group3.backend.DTOMappers;

import com.cs319group3.backend.DTOs.ClassProctoringTARelationDTO;
import com.cs319group3.backend.Entities.RelationEntities.ClassProctoringTARelation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClassProctoringTARelationMapper {




    public static ClassProctoringTARelationDTO essentialMapper(ClassProctoringTARelation classProctoringTARelation) {

        ClassProctoringTARelationDTO classProctoringTARelationDTO = new ClassProctoringTARelationDTO();

        classProctoringTARelationDTO.setClassProctoringDTO(ClassProctoringMapper.essentialMapper(classProctoringTARelation.getClassProctoring()));
        classProctoringTARelationDTO.setTAId(classProctoringTARelation.getTA().getUserId());

        classProctoringTARelationDTO.setPaid(classProctoringTARelation.isPaid());
        classProctoringTARelationDTO.setComplete(classProctoringTARelation.isComplete());
        classProctoringTARelationDTO.setIsOpenToSwap(classProctoringTARelation.isOpenToSwap());

        return classProctoringTARelationDTO;
    }

    public static List<ClassProctoringTARelationDTO> essentialMapper(List<ClassProctoringTARelation> classProctoringTARelations) {

        List<ClassProctoringTARelationDTO> dtos = new ArrayList<>();

        for (ClassProctoringTARelation classProctoringTARelation : classProctoringTARelations) {
            dtos.add(essentialMapper(classProctoringTARelation));
        }
        return dtos;
    }






}
