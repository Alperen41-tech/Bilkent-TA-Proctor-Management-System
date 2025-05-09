package com.cs319group3.backend.Services;

import com.cs319group3.backend.DTOs.CreateInstructorDTO;
import com.cs319group3.backend.DTOs.InstructorProfileDTO;

import java.util.List;

/**
 * Service interface for managing operations related to instructors,
 * including profile retrieval, creation, and listing.
 */
public interface InstructorService {

    /**
     * Retrieves the profile of an instructor by their user ID.
     *
     * @param id the user ID of the instructor
     * @return the profile data as an InstructorProfileDTO
     */
    InstructorProfileDTO getInstructorProfileById(int id);

    /**
     * Creates a new instructor account using the provided DTO.
     *
     * @param ciDTO the creation data for the instructor
     * @return true if creation is successful
     */
    boolean createInstructor(CreateInstructorDTO ciDTO);

    /**
     * Retrieves all instructors in the system.
     *
     * @return a list of InstructorProfileDTOs
     */
    List<InstructorProfileDTO> getAllInstructors();
}