package com.cs319group3.backend.Services.ServiceImpls.UserServiceImpls;

import com.cs319group3.backend.DTOMappers.InstructorProfileMapper;
import com.cs319group3.backend.DTOMappers.LoginMapper;
import com.cs319group3.backend.DTOs.CreateInstructorDTO;
import com.cs319group3.backend.DTOs.InstructorProfileDTO;
import com.cs319group3.backend.DTOs.LoginDTO;
import com.cs319group3.backend.Entities.Login;
import com.cs319group3.backend.Entities.UserEntities.Instructor;
import com.cs319group3.backend.Repositories.*;
import com.cs319group3.backend.Services.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InstructorServiceImpl implements InstructorService {

    @Autowired
    private InstructorRepo instructorRepo;

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private LoginMapper loginMapper;

    @Autowired
    private LoginRepo loginRepo;

    @Autowired
    private InstructorProfileMapper instructorProfileMapper;

    @Override
    public InstructorProfileDTO getInstructorProfileById(int id) {
        Optional<Instructor> optionalInstructor = instructorRepo.findByUserId(id);

        if (!optionalInstructor.isPresent()) {
            throw new RuntimeException("Instructor with ID " + id + " not found.");
        }

        return instructorProfileMapper.essentialMapper(optionalInstructor.get());
    }

    @Override
    public boolean createInstructor(CreateInstructorDTO ciDTO) {
        InstructorProfileDTO profile = ciDTO.getInstructorDTO();
        LoginDTO login = ciDTO.getLoginDTO();

        if (userRepo.findByBilkentId(profile.getBilkentId()).isPresent())
            throw new RuntimeException("User with ID " + profile.getBilkentId() + " already exists.");

        if (userRepo.findByEmail(profile.getEmail()).isPresent()) {
            throw new RuntimeException("instructor with email " + profile.getEmail() + " already exists.");
        }


        Instructor instructor = instructorProfileMapper.toEntity(profile);
        Login loginEntity = loginMapper.essentialEntityToLogin(login, instructor);

        if (loginEntity == null) {
            return false;
        }

        instructor.setDepartment(departmentRepo.findById(profile.getDepartmentId()).orElse(null));
        instructorRepo.save(instructor);
        loginRepo.save(loginEntity);

        return true;
    }

    @Override
    public List<InstructorProfileDTO> getAllInstructors() {
        List<Instructor> instructors = instructorRepo.findAll();
        List<InstructorProfileDTO> instructorProfileDTOs = new ArrayList<>();

        for (Instructor instructor : instructors) {
            instructorProfileDTOs.add(instructorProfileMapper.essentialMapper(instructor));
        }

        return instructorProfileDTOs;
    }
}