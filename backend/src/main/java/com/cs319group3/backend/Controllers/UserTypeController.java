package com.cs319group3.backend.Controllers;

import com.cs319group3.backend.Components.CurrentUserUtil;
import com.cs319group3.backend.Entities.UserType;
import com.cs319group3.backend.Services.UserTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller responsible for managing UserType operations such as creation
 * and retrieval of user types.
 */
@RestController
@RequestMapping("userType")
public class UserTypeController {

    @Autowired
    private UserTypeService userTypeService;

    @Autowired
    private CurrentUserUtil currentUserUtil;

    /**
     * Creates a new user type entity using only the type name as a request parameter.
     *
     * @param name the name of the user type
     * @return true if creation was successful
     */
    @PostMapping("createUserTypeEntity")
    public boolean createUserTypeEntity(@RequestParam String name) {
        System.out.println("Creating User Type Entity");
        return userTypeService.createUserType(name);
    }

    /**
     * Creates a new user type using a full UserType object as the request body.
     *
     * @param userType the UserType object
     * @return a ResponseEntity with creation result
     */
    @PostMapping("createUserType")
    public ResponseEntity<String> createUserType(@RequestBody UserType userType) {
        UserType u = new UserType();
        System.out.println(u.equals(userType));
        System.out.println(userType.getUserTypeName() + "Hello World");
        return userTypeService.createUserType(userType);
    }

    /**
     * Retrieves the UserType of the currently logged-in user.
     *
     * @return the UserType wrapped in a ResponseEntity
     */
    @GetMapping("getUserTypeById")
    public ResponseEntity<UserType> getUserTypeById() {
        int id = currentUserUtil.getCurrentUserId();
        return userTypeService.getUserById(id);
    }
}