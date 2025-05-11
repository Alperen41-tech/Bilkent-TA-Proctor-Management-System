package com.cs319group3.backend.DTOMappers;

import com.cs319group3.backend.DTOs.TAProfileDTO;
import com.cs319group3.backend.Entities.Course;
import com.cs319group3.backend.Entities.Department;
import com.cs319group3.backend.Entities.UserEntities.TA;
import com.cs319group3.backend.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TAProfileMapper {

    @Autowired
    CourseRepo courseRepo;

    @Autowired
    ClassProctoringTARelationRepo classProctoringTARelationRepo;

    @Autowired
    DepartmentRepo departmentRepo;
    @Autowired
    private StudentRepo studentRepo;

    public TAProfileDTO essentialMapper(TA ta) {
        TAProfileDTO dto = new TAProfileDTO();

        dto.setUserId(ta.getUserId());
        dto.setRole("Teaching Assistant"); //Should be changed
        dto.setEmail(ta.getEmail());
        dto.setName(ta.getName());
        dto.setSurname(ta.getSurname());
        dto.setBilkentId(ta.getBilkentId());
        dto.setPhoneNumber(ta.getPhoneNumber());
        dto.setActive(ta.isActive());
        dto.setClassYear(ta.getClassYear());
        dto.setWorkload(ta.getWorkload());
        dto.setPaidProctoringCount(classProctoringTARelationRepo.countClassProctoringTARelationsByTA_UserIdAndIsPaid(ta.getUserId(), true));
        
        dto.setClassNumber(studentRepo.findByBilkentId(ta.getBilkentId()).get().getClassYear());


        // Fix here:
        if (ta.getAssignedCourse() != null) {
            dto.setCourseName(ta.getAssignedCourse().getCourseName());
            dto.setCourseCode(ta.getAssignedCourse().getCourseCode());
        } else {
            dto.setCourseName(null); // or "Not Assigned"
        }

        if (ta.getDepartment() != null) {
            dto.setDepartmentName(ta.getDepartment().getDepartmentName());
            dto.setDepartmentCode(ta.getDepartment().getDepartmentCode());
        } else {
            dto.setDepartmentName(null);
        }

        return dto;
    }

    public List<TAProfileDTO> essentialMapper(List<TA> taList) {
        List<TAProfileDTO> dtoList = new ArrayList<>();
        for (TA ta : taList) {
            dtoList.add(essentialMapper(ta));
        }
        return dtoList;
    }

    public TA essentialEntityToTA(TAProfileDTO dto) {
        Course course = courseRepo.findByCourseCode(dto.getCourseCode())
                .orElse(null);
        Department department = departmentRepo.findByDepartmentCode(dto.getDepartmentCode())
                .orElse(null);

        if (department == null || course == null) {
            System.out.println("Bu ney be" + (department == null) + " "+ (course == null));
            return null;
        }

        TA ta = new TA();
        ta.setEmail(dto.getEmail());
        ta.setName(dto.getName());
        ta.setSurname(dto.getSurname());
        ta.setBilkentId(dto.getBilkentId());
        ta.setPhoneNumber(dto.getPhoneNumber());
        ta.setActive(dto.getActive());
        ta.setClassYear(dto.getClassYear());
        ta.setAssignedCourse(course);
        ta.setDepartment(department);
        return ta;
    }
}
