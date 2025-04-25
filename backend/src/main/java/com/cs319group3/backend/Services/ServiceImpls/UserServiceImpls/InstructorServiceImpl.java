package com.cs319group3.backend.Services.ServiceImpls.UserServiceImpls;

import com.cs319group3.backend.DTOMappers.InstructorProfileMapper;
import com.cs319group3.backend.DTOMappers.TaskTypeMapper;
import com.cs319group3.backend.DTOs.InstructorProfileDTO;
import com.cs319group3.backend.DTOs.TaskTypeDTO;
import com.cs319group3.backend.Entities.TaskType;
import com.cs319group3.backend.Entities.UserEntities.Instructor;
import com.cs319group3.backend.Repositories.CourseRepo;
import com.cs319group3.backend.Repositories.InstructorRepo;
import com.cs319group3.backend.Repositories.TaskTypeRepo;
import com.cs319group3.backend.Services.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service
public class InstructorServiceImpl implements InstructorService {

    @Autowired
    InstructorRepo instructorRepo;

    @Override
    public InstructorProfileDTO getInstructorProfileById(int id){
        Optional<Instructor> optionalInstructor = instructorRepo.findByUserId(id);

        if(!optionalInstructor.isPresent()) {
            throw new RuntimeException("Instructor with ID " + id + " not found.");
        }

        return InstructorProfileMapper.essentialMapper(optionalInstructor.get());
    }
}
