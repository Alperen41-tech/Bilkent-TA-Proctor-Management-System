package com.cs319group3.backend.Services.ServiceImpls.UserServiceImpls;

import com.cs319group3.backend.DTOMappers.DepartmentSecretaryProfileMapper;
import com.cs319group3.backend.DTOMappers.LoginMapper;
import com.cs319group3.backend.DTOs.CreateDepartmentSecretaryDTO;
import com.cs319group3.backend.DTOs.DepartmentSecretaryProfileDTO;
import com.cs319group3.backend.DTOs.LoginDTO;
import com.cs319group3.backend.Entities.Login;
import com.cs319group3.backend.Entities.UserEntities.DepartmentSecretary;
import com.cs319group3.backend.Repositories.DepartmentRepo;
import com.cs319group3.backend.Repositories.DepartmentSecretaryRepo;
import com.cs319group3.backend.Repositories.LoginRepo;
import com.cs319group3.backend.Repositories.UserRepo;
import com.cs319group3.backend.Services.DepartmentSecretaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DepartmentSecretaryServiceImpl implements DepartmentSecretaryService {

    @Autowired
    private DepartmentSecretaryRepo departmentSecretaryRepo;

    @Override
    public DepartmentSecretaryProfileDTO getDepartmentSecretaryProfileById(int id){
        Optional<DepartmentSecretary> departmentSecretary = departmentSecretaryRepo.findByUserId(id);

        if(departmentSecretary.isEmpty()){
            throw new RuntimeException("Department Secretary with ID " + id + " not found.");
        }

        return DepartmentSecretaryProfileMapper.essentialMapper(departmentSecretary.get());
    }

    @Autowired
    UserRepo userRepo;

    @Autowired
    LoginMapper loginMapper;

    @Autowired
    DepartmentRepo departmentRepo;

    @Autowired
    LoginRepo loginRepo;

    @Override
    public boolean createDepartmentSecretary(CreateDepartmentSecretaryDTO cdsDTO) {
        DepartmentSecretaryProfileDTO profile = cdsDTO.getProfile();
        LoginDTO login = cdsDTO.getLogin();

        if (userRepo.findByBilkentId(profile.getBilkentId()).isPresent() ||
                userRepo.findByEmail(profile.getEmail()).isPresent()) {
            return false; // Duplicate
        }

        DepartmentSecretary departmentSecretary = DepartmentSecretaryProfileMapper.toEntity(profile);
        Login loginEntity = loginMapper.essentialEntityToLogin(login, departmentSecretary);

        if(loginEntity == null) {
            return false;
        }

        departmentSecretary.setDepartment(departmentRepo.findById(profile.getDepartmentId()).orElse(null));
        departmentSecretaryRepo.save(departmentSecretary);
        loginRepo.save(loginEntity);
        return true;
    }
}
