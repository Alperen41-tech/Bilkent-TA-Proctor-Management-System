package com.cs319group3.backend.Services;

import com.cs319group3.backend.DTOs.ClassProctoringDTO;
import com.cs319group3.backend.DTOs.CreateClassProctoringDTO;

import java.util.List;

public interface ClassProctoringService {

    /**
     * Retrieves a list of class proctoring sessions specific to the current user (creator or related).
     *
     * @return a list of ClassProctoringDTO
     */
    List<ClassProctoringDTO> getClassProctoringList();

    /**
     * Creates a new class proctoring session using the provided DTO.
     *
     * @param dto the class proctoring creation data
     * @return true if the creation is successful, false otherwise
     */
    boolean createClassProctoring(CreateClassProctoringDTO dto);

    /**
     * Returns the number of unapproved requests sent for a specific class proctoring.
     *
     * @param classProctoringId the ID of the class proctoring
     * @return the number of unapproved requests
     */
    int numberOfRequestsSent(int classProctoringId);

    /**
     * Returns the number of TAs already assigned to a given class proctoring session.
     *
     * @param classProctoringId the ID of the class proctoring
     * @return the number of assigned TAs
     */
    int numberOfTAsAssigned(int classProctoringId);

    /**
     * Retrieves a list of all class proctoring sessions in the system.
     *
     * @return a list of ClassProctoringDTO
     */
    List<ClassProctoringDTO> getAllClassProctoring();
}