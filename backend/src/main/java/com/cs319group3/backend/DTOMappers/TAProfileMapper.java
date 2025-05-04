package com.cs319group3.backend.DTOMappers;

import com.cs319group3.backend.DTOs.TAProfileDTO;
import com.cs319group3.backend.Entities.Course;
import com.cs319group3.backend.Entities.Department;
import com.cs319group3.backend.Entities.UserEntities.TA;
import com.cs319group3.backend.Repositories.CourseRepo;
import com.cs319group3.backend.Repositories.DepartmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TAProfileMapper {

    @Autowired
    CourseRepo courseRepo;

    @Autowired
    DepartmentRepo departmentRepo;

    public static TAProfileDTO essentialMapper(TA ta) {
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

        // Fix here:
        if (ta.getAssignedCourse() != null) {
            dto.setCourseName(ta.getAssignedCourse().getCourseName());
            dto.setCourseCode(ta.getAssignedCourse().getCourseFullName());
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

    public static List<TAProfileDTO> essentialMapper(List<TA> taList) {
        List<TAProfileDTO> dtoList = new ArrayList<>();
        for (TA ta : taList) {
            dtoList.add(essentialMapper(ta));
        }
        return dtoList;
    }

    public TA essentialEntityToTA(TAProfileDTO dto) {
        Course course = courseRepo.findByCourseName(dto.getCourseName())
                .orElse(null);
        Department department = departmentRepo.findByDepartmentCode(dto.getDepartmentName())
                .orElse(null);

        if (department == null || course == null) {
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
