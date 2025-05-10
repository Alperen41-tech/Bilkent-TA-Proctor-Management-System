package com.cs319group3.backend.Services;

import com.cs319group3.backend.DTOs.ProctoringApplicationDTO;
import com.cs319group3.backend.DTOs.TAProfileDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProctoringApplicationTARelationService {

    /**
     * Creates a relation between a TA and a proctoring application.
     *
     * @param proctoringApplicationId the ID of the proctoring application
     * @param taId                    the ID of the TA
     * @return ResponseEntity containing true if creation is successful
     */
    ResponseEntity<Boolean> createProctoringApplicationTARelation(int proctoringApplicationId, int taId);

    /**
     * Returns a list of all TA applicants for the given proctoring application.
     *
     * @param applicationId the ID of the proctoring application
     * @return list of TAProfileDTO representing all applicants
     */
    List<TAProfileDTO> getAllApplicants(int applicationId);

    /**
     * Returns the number of applicants for a specific proctoring application.
     *
     * @param applicationId the ID of the proctoring application
     * @return number of TA applicants
     */
    int getApplicantCount(int applicationId);

    /**
     * Deletes the relation between a given TA and a class proctoring application.
     *
     * @param classProctoringId the ID of the class proctoring
     * @param applicant          the TAProfileDTO of the applicant to remove
     * @return true if the deletion is successful
     */
    boolean approveProctoringApplicationTARelation(int classProctoringId, TAProfileDTO applicant);
}