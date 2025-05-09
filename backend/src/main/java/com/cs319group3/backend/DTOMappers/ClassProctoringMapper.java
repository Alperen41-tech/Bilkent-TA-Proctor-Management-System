package com.cs319group3.backend.DTOMappers;


import com.cs319group3.backend.CompositeIDs.ClassProctoringClassroomKey;
import com.cs319group3.backend.DTOs.ClassProctoringDTO;
import com.cs319group3.backend.DTOs.CreateClassProctoringDTO;
import com.cs319group3.backend.Entities.ClassProctoring;
import com.cs319group3.backend.Entities.ClassProctoringClassroom;
import com.cs319group3.backend.Entities.Course;
import com.cs319group3.backend.Entities.UserEntities.Instructor;
import com.cs319group3.backend.Repositories.AuthStaffProctoringRequestRepo;
import com.cs319group3.backend.Repositories.ClassProctoringTARelationRepo;
import com.cs319group3.backend.Repositories.CourseRepo;
import com.cs319group3.backend.Repositories.InstructorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ClassProctoringMapper {

    @Autowired
    ClassProctoringTARelationRepo classProctoringTARelationRepo;

    @Autowired
    private AuthStaffProctoringRequestRepo authStaffProctoringRequestRepo;

    public ClassProctoringDTO essentialMapper(ClassProctoring classProctoring) {

        ClassProctoringDTO classProctoringDTO = new ClassProctoringDTO();

        classProctoringDTO.setId(classProctoring.getClassProctoringId());
        classProctoringDTO.setProctoringName(classProctoring.getEventName());
        classProctoringDTO.setCourseName(classProctoring.getCourse().getCourseName());
        classProctoringDTO.setStartDate(classProctoring.getStartDate());
        classProctoringDTO.setSection(classProctoring.getSectionNo());
        classProctoringDTO.setEndDate(classProctoring.getEndDate());
        List<String> tempClassrooms = classProctoring.getClassrooms().stream().map(cpc -> cpc.getId().getClassroom()).collect(Collectors.toList());
        classProctoringDTO.setCourseCode(classProctoring.getCourse().getCourseFullCode());
        classProctoringDTO.setDepartmentCode(classProctoring.getCourse().getDepartment().getDepartmentCode());

        StringBuffer strBuffer = new StringBuffer();
        int i= 0;
        for (String classroom : tempClassrooms) {
            if (i != 0) {
                strBuffer.append(" + ");
            }
            strBuffer.append(classroom);
            i++;
        }

        classProctoringDTO.setClassrooms(strBuffer.toString());

        classProctoringDTO.setTACount(classProctoring.getTACount());
        classProctoringDTO.setNumberOfAssignedTAs(classProctoringTARelationRepo.countAssignedTAs(classProctoring.getClassProctoringId()));
        classProctoringDTO.setNumberOfPendingRequests(authStaffProctoringRequestRepo.findByClassProctoringClassProctoringIdAndResponseDateIsNullAndApprovedFalse(classProctoring.getClassProctoringId()).size());

        return classProctoringDTO;
    }

}
