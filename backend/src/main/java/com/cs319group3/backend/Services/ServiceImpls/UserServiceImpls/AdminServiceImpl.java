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

import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public AdminProfileDTO getAdminProfile(int adminId) {
        Optional<User> user = userRepo.findById(adminId);
        if(user.isPresent()) {
            System.out.println("User of admin found");
            return AdminProfileMapper.toDTO(userRepo.findById(adminId).get());
        }
        else{
            System.out.println("User of admin not found");
            return null;
        }
    }

    @Autowired
    private UserTypeRepo userTypeRepo;

    @Autowired
    private LoginRepo loginRepo;

    @Override
    public boolean createAdmin(int userId, String password){
        Optional<User> optUser = userRepo.findById(userId);
        if(optUser.isEmpty()) {
            return false;
        }
        User user = optUser.get();
        Login login = new Login();
        login.setUserType(userTypeRepo.findByUserTypeName("admin"));
        login.setPassword(password);
        login.setUser(user);
        user.addLogin(login);
        loginRepo.save(login);
        return true;
    }
}
