package com.cs319group3.backend.Services;

import com.cs319group3.backend.DTOs.GeneralVariableDTO;

/**
 * Service interface for managing general system variables.
 */
public interface GeneralVariableService {

    /**
     * Changes the current academic semester.
     *
     * @param semester the new semester to be set (e.g., "2024-2025 Fall")
     * @return true if the update was successful, false otherwise
     */
    boolean changeSemester(String semester);

    /**
     * Changes the maximum number of proctoring assignments allowed.
     *
     * @param proctoringCap the new cap value
     * @return true if the update was successful, false otherwise
     */
    boolean changeProctoringCap(int proctoringCap);

    /**
     * Retrieves the current values of all general variables.
     *
     * @return a DTO containing general variable values
     */
    GeneralVariableDTO getGeneralVariable();
}