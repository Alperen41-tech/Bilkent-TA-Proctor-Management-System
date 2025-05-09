package com.cs319group3.backend.Services;

import com.cs319group3.backend.DTOs.CreateDepartmentSecretaryDTO;
import com.cs319group3.backend.DTOs.DepartmentSecretaryProfileDTO;

/**
 * Service interface for managing operations related to department secretaries,
 * including profile retrieval and account creation.
 */
public interface DepartmentSecretaryService {

    /**
     * Retrieves the profile of a department secretary by their user ID.
     *
     * @param id the user ID of the department secretary
     * @return the profile data as a DepartmentSecretaryProfileDTO
     */
    DepartmentSecretaryProfileDTO getDepartmentSecretaryProfileById(int id);

    /**
     * Creates a new department secretary account using the provided DTO.
     *
     * @param cdsDTO the creation data for the department secretary
     * @return true if creation is successful
     */
    boolean createDepartmentSecretary(CreateDepartmentSecretaryDTO cdsDTO);
}