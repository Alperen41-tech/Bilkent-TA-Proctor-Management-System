package com.cs319group3.backend.Services.ServiceImpls.UserServiceImpls;

import com.cs319group3.backend.DTOMappers.InstructorProfileMapper;
import com.cs319group3.backend.DTOMappers.LoginMapper;
import com.cs319group3.backend.DTOMappers.TAProfileMapper;
import com.cs319group3.backend.DTOMappers.TaskTypeMapper;
import com.cs319group3.backend.DTOs.CreateInstructorDTO;
import com.cs319group3.backend.DTOs.InstructorProfileDTO;
import com.cs319group3.backend.DTOs.LoginDTO;
import com.cs319group3.backend.DTOs.TaskTypeDTO;
import com.cs319group3.backend.Entities.Login;
import com.cs319group3.backend.Entities.TaskType;
import com.cs319group3.backend.Entities.UserEntities.Instructor;
import com.cs319group3.backend.Entities.UserEntities.TA;
import com.cs319group3.backend.Entities.UserType;
import com.cs319group3.backend.Repositories.*;
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

    @Autowired
    DepartmentRepo departmentRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    LoginMapper loginMapper;

    @Autowired
    LoginRepo loginRepo;

    @Autowired
    InstructorProfileMapper instructorProfileMapper;

    @Override
    public InstructorProfileDTO getInstructorProfileById(int id){
        Optional<Instructor> optionalInstructor = instructorRepo.findByUserId(id);

        if(!optionalInstructor.isPresent()) {
            throw new RuntimeException("Instructor with ID " + id + " not found.");
        }

        return instructorProfileMapper.essentialMapper(optionalInstructor.get());
    }


    @Override
    public boolean createInstructor(CreateInstructorDTO ciDTO) {
        InstructorProfileDTO profile = ciDTO.getInstructorDTO();
        LoginDTO login = ciDTO.getLoginDTO();

        // Check duplicate by bilkentId or email
        if (userRepo.findByBilkentId(profile.getBilkentId()).isPresent() ||
                userRepo.findByEmail(profile.getEmail()).isPresent()) {
            return false; // Duplicate
        }


        Instructor instructor = instructorProfileMapper.toEntity(profile);
        Login loginEntity = loginMapper.essentialEntityToLogin(login, instructor);

        if(loginEntity == null) {
            return false;
        }

        instructor.setDepartment(departmentRepo.findById(profile.getDepartmentId()).orElse(null));

        instructorRepo.save(instructor);
        loginRepo.save(loginEntity);

        return true;
    }
}
