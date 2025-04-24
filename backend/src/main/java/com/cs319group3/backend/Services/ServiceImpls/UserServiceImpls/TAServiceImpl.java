package com.cs319group3.backend.Services.ServiceImpls.UserServiceImpls;

import com.cs319group3.backend.DTOMappers.LoginMapper;
import com.cs319group3.backend.DTOs.*;
import com.cs319group3.backend.DTOMappers.TAProfileMapper;
import com.cs319group3.backend.Entities.*;
import com.cs319group3.backend.Entities.UserEntities.TA;
import com.cs319group3.backend.Repositories.*;
import com.cs319group3.backend.Services.TAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TAServiceImpl implements TAService {

    @Autowired
    private TARepo taRepository;
    @Autowired
    private StudentRepo studentRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserTypeRepo userTypeRepo;
    @Autowired
    private LoginRepo loginRepo;
    @Autowired
    private CourseRepo courseRepo;
    @Autowired
    private DepartmentRepo departmentRepo;

    @Override
    public TAProfileDTO getTAProfileById(int id) {
        Optional<TA> optionalTA = taRepository.findByUserId(id);

        if (optionalTA.isEmpty()) {
            throw new RuntimeException("TA with ID " + id + " not found.");
        }

        return TAProfileMapper.essentialMapper(optionalTA.get());
    }

    @Autowired
    LoginMapper loginMapper;
    @Autowired
    TAProfileMapper taProfileMapper;

    @Override
    public boolean createNewTA(CreateTADTO dto) {
        try {
            TAProfileDTO profile = dto.getProfile();
            LoginDTO login = dto.getLogin();

            // Check duplicate by bilkentId or email
            if (userRepo.findByBilkentId(profile.getBilkentId()).isPresent() ||
                    userRepo.findByEmail(profile.getEmail()).isPresent()) {
                return false; // Duplicate
            }


            TA ta = taProfileMapper.essentialEntityToTA(profile);
            taRepository.save(ta); // saves both into user and ta tables


            Login loginEntity = loginMapper.essentialEntityToLogin(login, ta);
            loginRepo.save(loginEntity);

            return true;
        } catch (Exception e) {
            // Log the exception for debugging
            e.printStackTrace();
            return false;
        }
    }
}