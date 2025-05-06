package com.cs319group3.backend.DTOMappers;


import com.cs319group3.backend.DTOs.CourseTAInstructorFormDTO;
import com.cs319group3.backend.Entities.CourseTAInstructorForm;
import com.cs319group3.backend.Entities.GeneralVariable;
import com.cs319group3.backend.Entities.Semester;
import com.cs319group3.backend.Repositories.CourseRepo;
import com.cs319group3.backend.Repositories.GeneralVariableRepo;
import com.cs319group3.backend.Repositories.InstructorRepo;
import com.cs319group3.backend.Repositories.SemesterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class CourseTAInstructorFormMapper {


    @Autowired
    private GeneralVariableRepo generalVariableRepo;
    @Autowired
    private SemesterRepo semesterRepo;


    public CourseTAInstructorForm essentialToEntityMapper(CourseTAInstructorFormDTO form) {



        CourseTAInstructorForm newForm = new CourseTAInstructorForm();

        newForm.setSentDate(LocalDateTime.now());
        newForm.setMinTALoad(form.getMinTALoad());
        newForm.setMaxTALoad(form.getMaxTALoad());
        newForm.setNumberOfGrader(form.getNumberOfGrader());
        newForm.setDescription(form.getDescription());
        newForm.setMustHaveTAs(form.getMustHaveTAs());
        newForm.setPreferredTAs(form.getPreferredTAs());
        newForm.setPreferredGraders(form.getPreferredGraders());
        newForm.setAvoidedTAs(form.getAvoidedTAs());

        int generalVariable = generalVariableRepo.getSemesterId();
        newForm.setSemester(semesterRepo.findById(generalVariable).get());

        return newForm;
    }



}
