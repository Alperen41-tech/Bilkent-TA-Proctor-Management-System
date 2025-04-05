package com.cs319group3.backend.Controllers;


import com.cs319group3.backend.Entities.UserType;
import com.cs319group3.backend.Services.UserTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("userType")
public class UserTypeController {



    @Autowired
    private UserTypeService userTypeService;



    @PostMapping("createUserType")
    public ResponseEntity<String> createUserType(@RequestBody UserType userType) {

        UserType u = new UserType();

        System.out.println(u.equals(userType));

        System.out.println(userType.getUserTypeName() + "Hello World") ;
        return userTypeService.createUserType(userType);
    }


    @PostMapping("deneme")
    public String denem(){
        return "deneme";
    }








}
