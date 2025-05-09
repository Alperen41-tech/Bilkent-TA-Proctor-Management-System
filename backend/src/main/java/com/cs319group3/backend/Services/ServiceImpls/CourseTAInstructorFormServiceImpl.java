package com.cs319group3.backend.Services.ServiceImpls;

import com.cs319group3.backend.DTOMappers.CourseTAInstructorFormMapper;
import com.cs319group3.backend.DTOs.CourseTAInstructorFormDTO;
import com.cs319group3.backend.Entities.Course;
import com.cs319group3.backend.Entities.CourseTAInstructorForm;
import com.cs319group3.backend.Entities.UserEntities.Instructor;
import com.cs319group3.backend.Repositories.CourseRepo;
import com.cs319group3.backend.Repositories.CourseTAInstructorFormRepo;
import com.cs319group3.backend.Repositories.InstructorRepo;
import com.cs319group3.backend.Services.CourseTAInstructorFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CourseTAInstructorFormServiceImpl implements CourseTAInstructorFormService {

    @Autowired
    private CourseTAInstructorFormMapper courseTAInstructorFormMapper;

    @Autowired
    private CourseRepo courseRepo;

    @Autowired
    private InstructorRepo instructorRepo;

    @Autowired
    private CourseTAInstructorFormRepo courseTAInstructorFormRepo;

    @Override
    public ResponseEntity<Boolean> createForm(CourseTAInstructorFormDTO form) {
        try {
            Optional<Instructor> instructor = instructorRepo.findByUserId(form.getInstructorId());
            if (instructor.isEmpty())
                throw new RuntimeException("instructor not found");

            Optional<Course> course = courseRepo.findByCourseId(form.getCourseId());
            if (course.isEmpty())
                throw new RuntimeException("course not found");

            Optional<CourseTAInstructorForm> sampleExist =
                    courseTAInstructorFormRepo.findByCourse_CourseId(course.get().getCourseId());
            if (sampleExist.isPresent())
                throw new RuntimeException("course ta request is already sent");

            CourseTAInstructorForm newForm = courseTAInstructorFormMapper.essentialToEntityMapper(form);
            newForm.setInstructor(instructor.get());
            newForm.setCourse(course.get());

            courseTAInstructorFormRepo.save(newForm);
            return new ResponseEntity<>(true, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }
}