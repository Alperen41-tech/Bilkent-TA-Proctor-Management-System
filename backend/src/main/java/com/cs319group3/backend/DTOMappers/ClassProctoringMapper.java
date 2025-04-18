package com.cs319group3.backend.DTOMappers;


import com.cs319group3.backend.DTOs.ClassProctoringDTO;
import com.cs319group3.backend.Entities.ClassProctoring;
import com.cs319group3.backend.Entities.ClassProctoringClassroom;

import java.util.List;
import java.util.stream.Collectors;

public class ClassProctoringMapper {


    public static ClassProctoringDTO essentialMapper(ClassProctoring classProctoring) {

        ClassProctoringDTO classProctoringDTO = new ClassProctoringDTO();

        classProctoringDTO.setId(classProctoring.getClassProctoringId());
        classProctoringDTO.setProctoringName(classProctoring.getTaskType().getTaskTypeName());
        classProctoringDTO.setCourseName(classProctoring.getCourse().getCourseName());
        classProctoringDTO.setStartDate(classProctoring.getStartDate());
        classProctoringDTO.setEndDate(classProctoring.getEndDate());
        List<String> tempClassrooms = classProctoring.getClassrooms().stream().map(cpc -> cpc.getId().getClassroom()).collect(Collectors.toList());

        StringBuffer strBuffer = new StringBuffer();

        for (String classroom : tempClassrooms) {
            strBuffer.append(" + " + classroom);
        }

        classProctoringDTO.setClassrooms(strBuffer.toString());

        return classProctoringDTO;
    }



}
