package com.cs319group3.backend.Controllers;


import com.cs319group3.backend.Components.CurrentUserUtil;
import com.cs319group3.backend.Entities.UserType;
import com.cs319group3.backend.Services.UserTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("userType")
public class UserTypeController {

    // method level comment

    @Autowired
    private UserTypeService userTypeService;
    @Autowired
    private CurrentUserUtil currentUserUtil;

    @PostMapping("createUserTypeEntity")
    public boolean createUserTypeEntity(@RequestParam String name) {
        System.out.println("Creating User Type Entity");
        return userTypeService.createUserType(name);
    }

    @PostMapping("createUserType")
    public ResponseEntity<String> createUserType(@RequestBody UserType userType) {

        UserType u = new UserType();

        System.out.println(u.equals(userType));

        System.out.println(userType.getUserTypeName() + "Hello World") ;
        return userTypeService.createUserType(userType);
    }


    @GetMapping("getUserTypeById")
    public ResponseEntity<UserType> getUserTypeById() {
        int id = currentUserUtil.getCurrentUserId();
        return userTypeService.getUserById(id);
    }
}
