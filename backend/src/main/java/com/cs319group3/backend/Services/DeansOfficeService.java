package com.cs319group3.backend.Services;

import com.cs319group3.backend.DTOs.CreateDeansOfficeDTO;
import com.cs319group3.backend.DTOs.DeansOfficeProfileDTO;

/**
 * Service interface for managing Dean's Office-related operations,
 * including profile retrieval and account creation.
 */
public interface DeansOfficeService {

    /**
     * Retrieves the profile of a Dean's Office member by their user ID.
     *
     * @param id the user ID of the Dean's Office member
     * @return the profile data as a DeansOfficeProfileDTO
     */
    DeansOfficeProfileDTO getDeansOfficeProfileById(int id);

    /**
     * Creates a new Dean's Office account based on the provided DTO.
     *
     * @param dto the data for the new Dean's Office user
     * @return true if creation was successful
     */
    boolean createDeansOffice(CreateDeansOfficeDTO dto);
}