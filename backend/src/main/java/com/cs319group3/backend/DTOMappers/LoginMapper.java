package com.cs319group3.backend.DTOMappers;

import com.cs319group3.backend.DTOs.LoginDTO;
import com.cs319group3.backend.Entities.Login;
import com.cs319group3.backend.Entities.UserEntities.DeansOffice;
import com.cs319group3.backend.Entities.UserEntities.DepartmentSecretary;
import com.cs319group3.backend.Entities.UserEntities.Instructor;
import com.cs319group3.backend.Entities.UserEntities.TA;
import com.cs319group3.backend.Entities.UserType;
import com.cs319group3.backend.Repositories.UserTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class LoginMapper {

    @Autowired
    private UserTypeRepo userTypeRepo;

    @Autowired
    private PasswordEncoder encoder;

    public Login essentialEntityToLogin(LoginDTO dto, TA ta) {
        Login login = new Login();
        login.setUser(ta);
        login.setPassword(encoder.encode(dto.getPassword()));

        UserType userType = userTypeRepo.findByUserTypeName("ta");
        login.setUserType(userType);
        return login;
    }

    public Login essentialEntityToLogin(LoginDTO dto, Instructor instructor) {
        Login login = new Login();
        login.setUser(instructor);
        login.setPassword(encoder.encode(dto.getPassword()));
        login.setUserType(userTypeRepo.findByUserTypeName("instructor"));
        return login;
    }

    public Login essentialEntityToLogin(LoginDTO dto, DepartmentSecretary departmentSecretary) {
        Login login = new Login();
        login.setUser(departmentSecretary);
        login.setPassword(encoder.encode(dto.getPassword()));
        login.setUserType(userTypeRepo.findByUserTypeName("department secretary"));
        return login;
    }

    public Login essentialEntityToLogin(LoginDTO dto, DeansOffice deansOffice) {
        Login login = new Login();
        login.setUser(deansOffice);
        login.setPassword(encoder.encode(dto.getPassword()));
        login.setUserType(userTypeRepo.findByUserTypeName("deans office"));
        return login;
    }
}
