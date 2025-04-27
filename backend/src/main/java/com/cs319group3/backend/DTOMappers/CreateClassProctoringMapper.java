package com.cs319group3.backend.DTOMappers;

import com.cs319group3.backend.CompositeIDs.ClassProctoringClassroomKey;
import com.cs319group3.backend.DTOs.CreateClassProctoringDTO;
import com.cs319group3.backend.Entities.ClassProctoring;
import com.cs319group3.backend.Entities.ClassProctoringClassroom;
import com.cs319group3.backend.Entities.Course;
import com.cs319group3.backend.Entities.UserEntities.Instructor;
import com.cs319group3.backend.Repositories.CourseRepo;
import com.cs319group3.backend.Repositories.InstructorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CreateClassProctoringMapper {
    @Autowired
    InstructorRepo instructorRepo;

    @Autowired
    CourseRepo courseRepo;

    public ClassProctoring essentialEntityTo(CreateClassProctoringDTO createClassProctoringDTO) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        Optional<Course> courseOpt = courseRepo.findByCourseName(createClassProctoringDTO.getCourseName());
        /*
        if (courseOpt.isEmpty()) {
            return null;
        }
        */
        Course course = courseOpt.get();
        Optional<Instructor> instructor = instructorRepo.findByUserId(course.getCoordinator().getUserId());


        ClassProctoring classProctoring = new ClassProctoring();
        classProctoring.setTACount(createClassProctoringDTO.getTaCount());
        classProctoring.setEventName(createClassProctoringDTO.getEventName());
        classProctoring.setTACount(createClassProctoringDTO.getTaCount());
        classProctoring.setSectionNo(createClassProctoringDTO.getSectionNo());
        classProctoring.setCreator(instructor.get());
        classProctoring.setCourse(course);
        classProctoring.setStartDate(LocalDateTime.parse(createClassProctoringDTO.getStartDate(), formatter));
        classProctoring.setEndDate(LocalDateTime.parse(createClassProctoringDTO.getEndDate(),formatter));
        classProctoring.setComplete(false);

        // Step: Create classroom entries
        List<ClassProctoringClassroom> classroomList = new ArrayList<>();
        for (String classroomName : createClassProctoringDTO.getClassrooms()) {
            ClassProctoringClassroom classroom = new ClassProctoringClassroom();

            ClassProctoringClassroomKey key = new ClassProctoringClassroomKey();
            // Do NOT set classProctoringId yet, since the ID will be auto-generated after save
            key.setClassroom(classroomName);
            classroom.setId(key);

            // Set the class proctoring reference
            classroom.setClassProctoring(classProctoring);

            classroomList.add(classroom);
        }

        // Link classrooms to the proctoring entity
        classProctoring.setClassrooms(classroomList);

        return classProctoring;
    }
}
