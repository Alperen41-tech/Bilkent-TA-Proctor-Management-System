package com.cs319group3.backend.Services.ServiceImpls;

import com.cs319group3.backend.DTOMappers.ClassProctoringMapper;
import com.cs319group3.backend.DTOMappers.ClassProctoringTARelationMapper;
import com.cs319group3.backend.DTOMappers.TAProfileMapper;
import com.cs319group3.backend.DTOs.ClassProctoringAndTAsDTO;
import com.cs319group3.backend.DTOs.ClassProctoringTARelationDTO;
import com.cs319group3.backend.DTOs.TAProfileDTO;
import com.cs319group3.backend.Entities.ClassProctoring;
import com.cs319group3.backend.Entities.Course;
import com.cs319group3.backend.Entities.RelationEntities.ClassProctoringTARelation;
import com.cs319group3.backend.Entities.RelationEntities.CourseInstructorRelation;
import com.cs319group3.backend.Entities.UserEntities.Instructor;
import com.cs319group3.backend.Entities.UserEntities.TA;
import com.cs319group3.backend.Repositories.*;
import com.cs319group3.backend.Services.ClassProctoringAndTAsService;
import com.cs319group3.backend.Services.TAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ClassProctoringAndTAsServiceImpl implements ClassProctoringAndTAsService {

    @Autowired
    private ClassProctoringTARelationRepo classProctoringTARelationRepo;

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private ClassProctoringRepo classProctoringRepo;

    @Autowired
    private TAProfileMapper taProfileMapper;

    @Autowired
    private ClassProctoringMapper classProctoringMapper;

    @Override
    public List<ClassProctoringAndTAsDTO> getDepartmentTAsClassProctorings(int userId) {
        return classProctoringToClassProctoringAndTAs(classProctoringRepo.findProctoringsByTAId(userId));
    }

    @Override
    public List<ClassProctoringAndTAsDTO> getDepartmentClassProctoringsByCode(String departmentCode) {
        return classProctoringToClassProctoringAndTAs(classProctoringRepo.findAllByDepartmentCode(departmentCode));
    }

    @Override
    public List<ClassProctoringAndTAsDTO> getDepartmentClassProctoringsById(int departmentId) {
        List<ClassProctoring> proctorings = classProctoringRepo.findByCourse_Department_DepartmentIdAndIsCompleteFalse(departmentId);
        return classProctoringToClassProctoringAndTAs(proctorings);
    }

    @Override
    public List<ClassProctoringAndTAsDTO> getFacultyClassProctorings(int facultyId) {
        List<Integer> departmentIds = departmentRepo.findDepartmentIdsByFacultyId(facultyId);
        List<ClassProctoringAndTAsDTO> results = new ArrayList<>();
        for (int departmentId : departmentIds) {
            results.addAll(getDepartmentClassProctoringsById(departmentId));
        }
        return results;
    }

    @Override
    public List<ClassProctoringAndTAsDTO> getClassProctoringsOfCreator(int creatorId) {
        List<ClassProctoring> proctorings = classProctoringRepo.findByCreatorUserId(creatorId);
        return classProctoringToClassProctoringAndTAs(proctorings);
    }

    @Override
    public List<ClassProctoringAndTAsDTO> getClassProctoringsOfInstructor(int instructorId) {
        return classProctoringToClassProctoringAndTAs(classProctoringRepo.findClassProctoringsByInstructorId(instructorId));
    }

    @Override
    public List<ClassProctoringAndTAsDTO> classProctoringToClassProctoringAndTAs(List<ClassProctoring> proctorings) {
        List<ClassProctoringAndTAsDTO> results = new ArrayList<>();
        for (ClassProctoring proctoring : proctorings) {
            List<TAProfileDTO> taList = new ArrayList<>();
            List<ClassProctoringTARelation> relations =
                    classProctoringTARelationRepo.findByClassProctoring_ClassProctoringId(proctoring.getClassProctoringId());

            for (ClassProctoringTARelation relation : relations) {
                TA ta = relation.getTA();
                if (ta != null) {
                    TAProfileDTO taDto = taProfileMapper.essentialMapper(ta);
                    taList.add(taDto);
                }
            }

            ClassProctoringAndTAsDTO dto = new ClassProctoringAndTAsDTO();
            ClassProctoringTARelationDTO taDTO = new ClassProctoringTARelationDTO();
            taDTO.setClassProctoringDTO(classProctoringMapper.essentialMapper(proctoring));
            dto.setClassProctoringTARelationDTO(taDTO);
            dto.setTaProfileDTOList(taList);
            results.add(dto);
        }
        return results;
    }
}