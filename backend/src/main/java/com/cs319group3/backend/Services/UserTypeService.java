package com.cs319group3.backend.Services;

import com.cs319group3.backend.Entities.UserType;
import org.springframework.http.ResponseEntity;

/**
 * Service interface for managing UserType entities.
 */
public interface UserTypeService {

    /**
     * Creates a user type using a UserType entity.
     *
     * @param userType the UserType entity to create
     * @return a response indicating the result of the operation
     */
    ResponseEntity<String> createUserType(UserType userType);

    /**
     * Creates a user type using a name.
     *
     * @param name the name of the user type
     * @return true if creation is successful, false otherwise
     */
    boolean createUserType(String name);

    /**
     * Retrieves a UserType entity by its ID.
     *
     * @param id the ID of the user type
     * @return the UserType wrapped in a ResponseEntity
     */
    ResponseEntity<UserType> getUserById(int id);
}