package com.cs319group3.backend.DTOMappers;

import com.cs319group3.backend.DTOs.LoginDTO;
import com.cs319group3.backend.Entities.Login;
import com.cs319group3.backend.Entities.UserEntities.TA;
import com.cs319group3.backend.Repositories.UserTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginMapper {

    @Autowired
    UserTypeRepo userTypeRepo;

    public Login essentialEntityToLogin(LoginDTO dto, TA ta) {
        Login login = new Login();
        login.setUser(ta); // because TA extends User
        login.setPassword(dto.getPassword());
        login.setUserType(userTypeRepo.findByUserTypeName("ta"));
        return login;
    }
}
