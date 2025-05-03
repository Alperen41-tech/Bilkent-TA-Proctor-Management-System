package com.cs319group3.backend.Services.ServiceImpls.UserServiceImpls;

import com.cs319group3.backend.DTOMappers.AdminProfileMapper;
import com.cs319group3.backend.DTOs.AdminProfileDTO;
import com.cs319group3.backend.Repositories.UserRepo;
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
}
