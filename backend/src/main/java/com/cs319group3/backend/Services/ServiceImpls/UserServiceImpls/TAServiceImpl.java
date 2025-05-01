package com.cs319group3.backend.Services.ServiceImpls.UserServiceImpls;

import com.cs319group3.backend.DTOMappers.LoginMapper;
import com.cs319group3.backend.DTOs.*;
import com.cs319group3.backend.DTOMappers.TAProfileMapper;
import com.cs319group3.backend.Entities.*;
import com.cs319group3.backend.Entities.UserEntities.TA;
import com.cs319group3.backend.Repositories.*;
import com.cs319group3.backend.Services.TAService;
import com.cs319group3.backend.Services.TAWorkloadRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TAServiceImpl implements TAService {

    @Autowired
    private TARepo taRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private LoginRepo loginRepo;
    @Autowired
    private ClassProctoringTARelationRepo classProctoringTARelationRepo;

    @Autowired
    private TAWorkloadRequestService taWorkloadRequestService;


    @Override
    public TAProfileDTO getTAProfileById(int id) {
        Optional<TA> optionalTA = taRepo.findByUserId(id);

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
            Login loginEntity = loginMapper.essentialEntityToLogin(login, ta);

            if(ta == null || loginEntity == null) {
                return false;
            }

            taRepo.save(ta); // saves both into user and ta tables
            loginRepo.save(loginEntity);

            return true;
        } catch (Exception e) {
            // Log the exception for debugging
            e.printStackTrace();
            return false;
        }
    }

    @Autowired
    DepartmentRepo departmentRepo;

    @Override
    public List<TAProfileDTO> getAllAvailableTAsByDepartmentCode(String departmentCode, int classProctoringId) {
        List<TA> availableTAs = taRepo.findAvailableTAsByDepartment(departmentCode, classProctoringId);
        List<TAProfileDTO> availableTAProfiles = new ArrayList<>();
        for (TA ta : availableTAs) {
            TAProfileDTO profile = TAProfileMapper.essentialMapper(ta);
            availableTAProfiles.add(profile);
        }
        return availableTAProfiles;
    }

    @Override
    public List<TAProfileDTO> getAllAvailableTAsByFacultyId(int facultyId, int classProctoringId) {
        List<TA> availableTAs = taRepo.findAvailableTAsByFaculty(facultyId, classProctoringId);
        List<TAProfileDTO> availableTAProfiles = new ArrayList<>();
        for (TA ta : availableTAs) {
            TAProfileDTO profile = TAProfileMapper.essentialMapper(ta);
            availableTAProfiles.add(profile);
        }
        return availableTAProfiles;
    }


    @Override
    public List<TAProfileDTO> getTAsByProctoringId(int proctoringId) {
        List<TA> tas = classProctoringTARelationRepo.findTAsByClassProctoringId(proctoringId);
        List<TAProfileDTO> availableTAProfiles = new ArrayList<>();
        for (TA ta : tas) {
            TAProfileDTO profile = TAProfileMapper.essentialMapper(ta);
            availableTAProfiles.add(profile);
        }
        return availableTAProfiles;
    }

    @Override
    public List<TAProfileDTO> getAllTAProfiles() {
        List<TA> tas = taRepo.findAll();
        List<TAProfileDTO> TAProfiles = new ArrayList<>();
        for (TA ta : tas) {
            TAProfileDTO dto = TAProfileMapper.essentialMapper(ta);
            dto.setWorkload(taWorkloadRequestService.getTotalWorkload(ta.getUserId()));
            TAProfiles.add(dto);
        }
        return TAProfiles;
    }
}