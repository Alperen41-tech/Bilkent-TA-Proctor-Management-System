package com.cs319group3.backend.Services;


import com.cs319group3.backend.Entities.UserType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


public interface UserTypeService {


    /**
     * creates user type according to given data
     * @param userType
     * @return
     */
    ResponseEntity<String> createUserType(UserType userType);


    ResponseEntity<UserType> getUserById(int id);


}
