package com.cs319group3.backend.DTOMappers;

import com.cs319group3.backend.DTOs.InstructorProfileDTO;
import com.cs319group3.backend.Entities.RelationEntities.CourseInstructorRelation;
import com.cs319group3.backend.Entities.UserEntities.Instructor;

import java.util.ArrayList;
import java.util.List;

public class InstructorProfileMapper {

    public static InstructorProfileDTO essentialMapper(Instructor instructor) {
        InstructorProfileDTO instructorProfileDTO = new InstructorProfileDTO();
        instructorProfileDTO.setRole("Instructor"); //Should be changed
        instructorProfileDTO.setName(instructor.getName());
        instructorProfileDTO.setSurname(instructor.getSurname());
        instructorProfileDTO.setEmail(instructor.getEmail());
        instructorProfileDTO.setBilkentId(instructor.getBilkentId());
        instructorProfileDTO.setDepartmentName(instructor.getDepartment().getDepartmentName());

        List<String> courseNames = new ArrayList<>();
        for (CourseInstructorRelation relation : instructor.getCourseInstructorRelations()) {
            courseNames.add(relation.getCourse().getCourse().getCourseName());
        }
        instructorProfileDTO.setCourses(courseNames);
        return instructorProfileDTO;
    }
}
