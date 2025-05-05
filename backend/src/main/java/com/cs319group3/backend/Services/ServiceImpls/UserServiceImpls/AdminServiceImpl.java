package com.cs319group3.backend.Services.ServiceImpls.UserServiceImpls;

import com.cs319group3.backend.DTOMappers.AdminProfileMapper;
import com.cs319group3.backend.DTOs.AdminProfileDTO;
import com.cs319group3.backend.Entities.Login;
import com.cs319group3.backend.Entities.UserEntities.User;
import com.cs319group3.backend.Repositories.LoginRepo;
import com.cs319group3.backend.Repositories.UserRepo;
import com.cs319group3.backend.Repositories.UserTypeRepo;
import com.cs319group3.backend.Services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    UserRepo userRepo;

    @Override
    public AdminProfileDTO getAdminProfile(int adminId) {
        return AdminProfileMapper.toDTO(userRepo.findById(adminId).get());
    }

    @Autowired
    UserTypeRepo userTypeRepo;

    @Autowired
    LoginRepo loginRepo;

    @Override
    public boolean createAdmin(int userId, String password){
        User user = userRepo.findById(userId).get();
        Login login = new Login();
        login.setUserType(userTypeRepo.findByUserTypeName("admin"));
        login.setPassword(password);
        login.setUser(user);
        user.addLogin(login);
        loginRepo.save(login);
        return true;
    }
}
