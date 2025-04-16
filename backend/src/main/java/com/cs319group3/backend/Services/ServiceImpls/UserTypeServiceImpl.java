package com.cs319group3.backend.Services.ServiceImpls;

import com.cs319group3.backend.Entities.UserEntities.User;
import com.cs319group3.backend.Entities.UserType;
import com.cs319group3.backend.Repositories.UserTypeRepo;
import com.cs319group3.backend.Services.UserTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserTypeServiceImpl implements UserTypeService {

    @Autowired
    private UserTypeRepo userTypeDAO;


    @Override
    public ResponseEntity<String> createUserType(UserType userType) {
        userTypeDAO.save(userType);
        return new ResponseEntity<>("Succesfully created user type", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<UserType> getUserById(int id) {
        Optional<UserType> type = userTypeDAO.findById(id);


        if (type.isPresent()) {
            return new ResponseEntity<>(type.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
