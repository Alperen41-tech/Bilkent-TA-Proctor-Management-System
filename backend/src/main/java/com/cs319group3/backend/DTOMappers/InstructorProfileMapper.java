package com.cs319group3.backend.DTOMappers;

import com.cs319group3.backend.DTOs.InstructorProfileDTO;
import com.cs319group3.backend.Entities.Course;
import com.cs319group3.backend.Entities.OfferedCourse;
import com.cs319group3.backend.Entities.RelationEntities.CourseInstructorRelation;
import com.cs319group3.backend.Entities.UserEntities.Instructor;
import com.cs319group3.backend.Repositories.GeneralVariableRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class InstructorProfileMapper {

    @Autowired
    private GeneralVariableRepo generalVariableRepo;

    public InstructorProfileDTO essentialMapper(Instructor instructor) {
        InstructorProfileDTO instructorProfileDTO = new InstructorProfileDTO();
        instructorProfileDTO.setRole("instructor");
        instructorProfileDTO.setName(instructor.getName());
        instructorProfileDTO.setSurname(instructor.getSurname());
        instructorProfileDTO.setEmail(instructor.getEmail());
        instructorProfileDTO.setBilkentId(instructor.getBilkentId());
        instructorProfileDTO.setDepartmentName(instructor.getDepartment().getDepartmentName());

        List<String> courseNames = getStrings(instructor);

        instructorProfileDTO.setCourses(courseNames);
        return instructorProfileDTO;
    }

    private List<String> getStrings(Instructor instructor) {
        List<String> courseNames = new ArrayList<>();
        Set<String> seen = new HashSet<>();

        for (CourseInstructorRelation relation : instructor.getCourseInstructorRelations()) {
            OfferedCourse c = relation.getCourse();
            int currentSemesterId = generalVariableRepo.getSemesterId();
            if (c.getSemester().getSemesterId() == currentSemesterId){
                String courseName = relation.getCourse().getCourse().getCourseName();
                courseName = courseName + " - " + relation.getCourse().getCourse().getCourseFullName();
                if (seen.add(courseName)) { // add returns false if courseName is already in the set
                    courseNames.add(courseName);
                }
            }
        }
        return courseNames;
    }

    public Instructor toEntity(InstructorProfileDTO instructorDTO) {
        Instructor instructor = new Instructor();
        instructor.setName(instructorDTO.getName());
        instructor.setSurname(instructorDTO.getSurname());
        instructor.setEmail(instructorDTO.getEmail());
        instructor.setBilkentId(instructorDTO.getBilkentId());
        instructor.setPhoneNumber(instructorDTO.getPhoneNumber());
        instructor.setActive(instructorDTO.isActive());
        instructor.setPhoneNumber(instructorDTO.getPhoneNumber());
        return instructor;
    }
}
