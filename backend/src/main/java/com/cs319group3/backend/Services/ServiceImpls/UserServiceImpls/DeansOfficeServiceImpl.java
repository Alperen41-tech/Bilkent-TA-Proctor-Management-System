package com.cs319group3.backend.Services.ServiceImpls.UserServiceImpls;

import com.cs319group3.backend.DTOMappers.DeansOfficeProfileMapper;
import com.cs319group3.backend.DTOMappers.LoginMapper;
import com.cs319group3.backend.DTOs.CreateDeansOfficeDTO;
import com.cs319group3.backend.DTOs.DeansOfficeProfileDTO;
import com.cs319group3.backend.DTOs.LoginDTO;
import com.cs319group3.backend.Entities.Faculty;
import com.cs319group3.backend.Entities.Login;
import com.cs319group3.backend.Entities.UserEntities.DeansOffice;
import com.cs319group3.backend.Repositories.DeansOfficeRepo;
import com.cs319group3.backend.Repositories.FacultyRepo;
import com.cs319group3.backend.Repositories.LoginRepo;
import com.cs319group3.backend.Repositories.UserRepo;
import com.cs319group3.backend.Services.DeansOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeansOfficeServiceImpl implements DeansOfficeService {

    @Autowired
    private DeansOfficeRepo deansOfficeRepo;

    @Autowired
    private LoginMapper loginMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private LoginRepo loginRepo;

    @Autowired
    private FacultyRepo facultyRepo;

    @Override
    public DeansOfficeProfileDTO getDeansOfficeProfileById(int id) {
        Optional<DeansOffice> deansOffice = deansOfficeRepo.findByUserId(id);
        if (!deansOffice.isPresent()) {
            throw new RuntimeException("DeansOffice with ID " + id + " not found.");
        }
        DeansOffice deansOfficeEntity = deansOffice.get();
        return DeansOfficeProfileMapper.essentialMapper(deansOfficeEntity);
    }

    @Override
    public boolean createDeansOffice(CreateDeansOfficeDTO cdoDTO) {
        DeansOfficeProfileDTO profile = cdoDTO.getProfile();
        LoginDTO login = cdoDTO.getLogin();

        if (userRepo.findByBilkentId(profile.getBilkentId()).isPresent() ||
                userRepo.findByEmail(profile.getEmail()).isPresent()) {
            return false;
        }

        DeansOffice deansOffice = DeansOfficeProfileMapper.toEntity(profile);
        Optional<Faculty> facultyOpt = facultyRepo.findById(profile.getFacultyId());
        if(facultyOpt.isEmpty()) {
            System.out.println("Faculty not found");
            return false;
        }
        deansOffice.setFaculty(facultyOpt.get());
        Login loginEntity = loginMapper.essentialEntityToLogin(login, deansOffice);

        if (loginEntity == null) {
            return false;
        }

        deansOfficeRepo.save(deansOffice);
        loginRepo.save(loginEntity);
        return true;
    }
}