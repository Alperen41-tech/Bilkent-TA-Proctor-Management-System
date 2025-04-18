package com.cs319group3.backend.DTOMappers;

import com.cs319group3.backend.DTOs.TAProfileDTO;
import com.cs319group3.backend.Entities.UserEntities.TA;

public class TAProfileMapper {
    public static TAProfileDTO essentialMapper(TA ta) {
        TAProfileDTO dto = new TAProfileDTO();

        dto.setRole("Teaching Assistant");
        dto.setEmail(ta.getEmail());
        dto.setName(ta.getName());
        dto.setSurname(ta.getSurname());
        dto.setBilkentId(ta.getBilkentId());

        // Fix here:
        if (ta.getAssignedCourse() != null) {
            dto.setCourseName(ta.getAssignedCourse().getCourseName());
        } else {
            dto.setCourseName(null); // or "Not Assigned"
        }

        if (ta.getDepartment() != null) {
            dto.setDepartmentName(ta.getDepartment().getDepartmentName());
        } else {
            dto.setDepartmentName(null);
        }

        return dto;
    }
}
