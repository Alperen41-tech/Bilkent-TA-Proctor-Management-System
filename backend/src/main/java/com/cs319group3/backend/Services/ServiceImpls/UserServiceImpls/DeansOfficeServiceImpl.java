package com.cs319group3.backend.Services.ServiceImpls.UserServiceImpls;

import com.cs319group3.backend.DTOMappers.DeansOfficeProfileMapper;
import com.cs319group3.backend.DTOMappers.LoginMapper;
import com.cs319group3.backend.DTOs.CreateDeansOfficeDTO;
import com.cs319group3.backend.DTOs.DeansOfficeProfileDTO;
import com.cs319group3.backend.DTOs.LoginDTO;
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

    @Override
    public DeansOfficeProfileDTO getDeansOfficeProfileById(int id) {
        Optional<DeansOffice> deansOffice = deansOfficeRepo.findByUserId(id);
        if (!deansOffice.isPresent()) {
            throw new RuntimeException("DeansOffice with ID " + id + " not found.");
        }
        DeansOffice deansOfficeEntity = deansOffice.get();

        return DeansOfficeProfileMapper.essentialMapper(deansOfficeEntity);
    }

    @Autowired
    UserRepo userRepo;

    @Autowired
    LoginRepo loginRepo;

    @Autowired
    FacultyRepo facultyRepo;

    @Override
    public boolean createDeansOffice(CreateDeansOfficeDTO cdoDTO){
        DeansOfficeProfileDTO profile = cdoDTO.getProfile();
        LoginDTO login = cdoDTO.getLogin();

        if (userRepo.findByBilkentId(profile.getBilkentId()).isPresent() ||
                userRepo.findByEmail(profile.getEmail()).isPresent()) {
            return false; // Duplicate
        }

        DeansOffice deansOffice = DeansOfficeProfileMapper.toEntity(profile);
        deansOffice.setFaculty(facultyRepo.findByFacultyId(profile.getFacultyId()).get());
        Login loginEntity = loginMapper.essentialEntityToLogin(login, deansOffice);

        if(loginEntity == null){
            return false;
        }

        deansOfficeRepo.save(deansOffice);
        loginRepo.save(loginEntity);
        return true;
    }
}
