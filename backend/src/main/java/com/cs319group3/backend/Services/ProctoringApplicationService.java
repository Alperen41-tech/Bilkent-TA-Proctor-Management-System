package com.cs319group3.backend.Services;

import com.cs319group3.backend.DTOs.ProctoringApplicationDTO;
import com.cs319group3.backend.Enums.ProctoringApplicationType;

import java.util.List;

public interface ProctoringApplicationService {

    /**
     * Retrieves all proctoring applications received by a Dean's Office.
     *
     * @param deansOfficeId ID of the Dean's Office
     * @return List of ProctoringApplicationDTOs
     */
    List<ProctoringApplicationDTO> getProctoringApplications(int deansOfficeId);

    /**
     * Creates a single proctoring application for a class.
     *
     * @param classProctoringId ID of the class proctoring
     * @param dto DTO representing the proctoring application
     * @param deansOfficeId ID of the Dean's Office creating the application
     * @return true if successful, false otherwise
     */
    boolean createProctoringApplication(int classProctoringId, ProctoringApplicationDTO dto, int deansOfficeId);

    /**
     * Creates multiple proctoring applications for a class.
     *
     * @param classProctoringId ID of the class proctoring
     * @param dto List of DTOs representing the applications
     * @param deansOfficeId ID of the Dean's Office
     * @return true if all applications are created successfully
     */
    boolean createProctoringApplications(int requestId, int classProctoringId, List<ProctoringApplicationDTO> dto, int deansOfficeId);

    /**
     * Retrieves all proctoring applications related to a specific department.
     *
     * @param departmentId ID of the department
     * @return List of ProctoringApplicationDTOs
     */
    List<ProctoringApplicationDTO> getAllApplicationsByDepartment(int departmentSecretaryId);

    /**
     * Sets the application type for a specific proctoring application.
     *
     * @param applicationId ID of the application
     * @param type New type to assign
     * @return true if update was successful
     */
    boolean setApplicationType(int applicationId, ProctoringApplicationType type);

    /**
     * Retrieves all applications for a given TA by application type.
     *
     * @param userId TA user ID
     * @param applicationType Type of applications to retrieve
     * @return List of ProctoringApplicationDTOs
     */
    List<ProctoringApplicationDTO> getAllApplicationsForTA(int userId, ProctoringApplicationType applicationType);
}