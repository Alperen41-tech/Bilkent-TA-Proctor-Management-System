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
import java.util.stream.Collectors;

@Service
public class ClassProctoringAndTAsServiceImpl implements ClassProctoringAndTAsService {
    @Autowired
    private TARepo taRepo;

    @Autowired
    private ClassProctoringTARelationRepo classProctoringTARelationRepo;
    @Autowired
    private DepartmentRepo departmentRepo;
    @Autowired
    private CourseRepo courseRepo;
    @Autowired
    private ClassProctoringRepo classProctoringRepo;
    @Autowired
    private InstructorRepo instructorRepo;

    @Override
    public List<ClassProctoringAndTAsDTO> getDepartmentTAsClassProctorings(int userId) {
        return classProctoringToClassProctoringAndTAs(classProctoringRepo.findProctoringsByTAId(userId));
    }

    @Autowired
    DeansOfficeRepo deansOfficeRepo;
    @Override
    public List<ClassProctoringAndTAsDTO> getDepartmentClassProctoringsByCode(String departmentCode) {
        return classProctoringToClassProctoringAndTAs(classProctoringRepo.findAllByDepartmentCode(departmentCode));
    }

    @Override
    public List<ClassProctoringAndTAsDTO> getDepartmentClassProctoringsById(int departmentId) {
        // Fetch all proctorings in the department
        List<ClassProctoring> proctorings = classProctoringRepo.findByCourse_Department_DepartmentIdAndIsCompleteFalse(departmentId);

        return classProctoringToClassProctoringAndTAs(proctorings);
    }

    @Override
    public List<ClassProctoringAndTAsDTO> getFacultyClassProctorings(int facultyId) {
        List<Integer> departmentIds = departmentRepo.findDepartmentIdsByFacultyId(facultyId);

        List<ClassProctoringAndTAsDTO> results = new ArrayList<>();
        for (int departmentId : departmentIds) {
            // Fetch all relations in the department
            results.addAll(getDepartmentClassProctoringsById(departmentId));
        }

        return results;
    }

    @Autowired
    TAService taService;

    @Override
    public List<ClassProctoringAndTAsDTO> getClassProctoringsOfCreator(int creatorId) {
        List<ClassProctoring> proctorings = classProctoringRepo.findByCreatorUserId(creatorId);

        return classProctoringToClassProctoringAndTAs(proctorings);
    }



    @Override
    public List<ClassProctoringAndTAsDTO> getClassProctoringsOfInstructor(int instructorId){
        return classProctoringToClassProctoringAndTAs(classProctoringRepo.findClassProctoringsByInstructorId(instructorId));
    }

    @Override
    public List<ClassProctoringAndTAsDTO> classProctoringToClassProctoringAndTAs(List<ClassProctoring> proctorings){
        List<ClassProctoringAndTAsDTO> results = new ArrayList<>();
        for (ClassProctoring proctoring : proctorings) {
            List<TAProfileDTO> taList = new ArrayList<>();

            //Find all rows with the class proctoring id of the proctoring and put the TA into the taList
            List<ClassProctoringTARelation> relations =
                    classProctoringTARelationRepo.findByClassProctoring_ClassProctoringId(proctoring.getClassProctoringId());

            for (ClassProctoringTARelation relation : relations) {
                TA ta = relation.getTA();
                if (ta != null) {
                    TAProfileDTO taDto = TAProfileMapper.essentialMapper(ta);
                    taList.add(taDto);
                }
            }

            ClassProctoringAndTAsDTO dto = new ClassProctoringAndTAsDTO();
            ClassProctoringTARelationDTO taDTO = new ClassProctoringTARelationDTO();
            taDTO.setClassProctoringDTO(ClassProctoringMapper.essentialMapper(proctoring));
            dto.setClassProctoringTARelationDTO(taDTO);
            dto.setTaProfileDTOList(taList);
            results.add(dto);
        }

        return results;
    }

}
