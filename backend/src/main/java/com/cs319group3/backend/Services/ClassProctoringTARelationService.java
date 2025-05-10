package com.cs319group3.backend.Services;

import com.cs319group3.backend.DTOs.ClassProctoringDTO;
import com.cs319group3.backend.DTOs.ClassProctoringTARelationDTO;
import com.cs319group3.backend.DTOs.TAProfileDTO;
import com.cs319group3.backend.Entities.RelationEntities.ClassProctoringTARelation;

import java.util.List;

public interface ClassProctoringTARelationService {

    /**
     * Retrieves all class proctorings assigned to the given TA.
     *
     * @param id the user ID of the TA
     * @return list of class proctoring relation DTOs
     * @throws Exception if the TA cannot be found or an error occurs
     */
    List<ClassProctoringTARelationDTO> getTAsClassProctoringDTOs(int id) throws Exception;

    /**
     * Updates a class proctoring record assigned to a TA.
     *
     * @param dto     the updated class proctoring TA relation
     * @param userId  the ID of the user performing the update
     * @return true if the update is successful
     */
    boolean updateClassProctoringDTO(ClassProctoringTARelationDTO dto, int userId);

    /**
     * Removes a TA from a specific class proctoring assignment.
     *
     * @param taId             the ID of the TA to remove
     * @param classProctoringId the ID of the class proctoring
     * @param removerId        the ID of the user requesting removal
     * @return true if removal was successful
     */
    boolean removeTAFromClassProctoring(int taId, int classProctoringId, int removerId);

    /**
     * Creates a new assignment of a TA to a class proctoring.
     *
     * @param taId              the ID of the TA
     * @param classProctoringId the ID of the class proctoring
     * @return true if creation is successful
     */
    boolean createClassProctoringTARelation(int taId, int classProctoringId);

    /**
     * Retrieves class proctorings related to the TA's department.
     *
     * @param taId the ID of the TA
     * @return list of related class proctoring relation DTOs
     * @throws Exception if an error occurs while fetching data
     */
    List<ClassProctoringTARelationDTO> getTAsClassProctoringsByDepartment(int taId) throws Exception;

    boolean assignTAFromOtherFaculty(TAProfileDTO dto, int classProctoringId);
}