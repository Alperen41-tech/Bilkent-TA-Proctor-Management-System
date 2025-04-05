package com.cs319group3.backend.Services.ServiceImpls;

import com.cs319group3.backend.Entities.UserType;
import com.cs319group3.backend.Repositories.UserTypeDAO;
import com.cs319group3.backend.Services.UserTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class UserTypeServiceImpl implements UserTypeService {

    @Autowired
    private UserTypeDAO userTypeDAO;


    @Override
    public ResponseEntity<String> createUserType(UserType userType) {
        userTypeDAO.save(userType);
        return new ResponseEntity<>("Succesfully created user type", HttpStatus.CREATED);
    }
}
