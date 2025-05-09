package com.cs319group3.backend.Services;

import com.cs319group3.backend.DTOs.CreateTADTO;
import com.cs319group3.backend.DTOs.TAProfileDTO;
import com.cs319group3.backend.Entities.ClassProctoring;
import com.cs319group3.backend.Entities.UserEntities.TA;

import java.util.List;

/**
 * Service interface for managing Teaching Assistant (TA) operations such as profile access,
 * availability checks, course eligibility, and account creation.
 */
public interface TAService {

    /**
     * Retrieves the TA profile by user ID.
     *
     * @param id the user ID of the TA
     * @return the TA's profile
     */
    TAProfileDTO getTAProfileById(int id);

    /**
     * Creates a new Teaching Assistant user.
     *
     * @param createTADTO the DTO containing TA and login data
     * @return true if creation is successful
     */
    boolean createNewTA(CreateTADTO createTADTO);

    /**
     * Returns a list of available TAs in the specified department who are not already assigned to a given proctoring.
     *
     * @param departmentCode the department code
     * @param proctoringId the class proctoring ID
     * @param userId the ID of the user making the request (e.g. Dean's Office or Instructor)
     * @return a list of TA profiles
     */
    List<TAProfileDTO> getAllAvailableTAsByDepartmentCode(String departmentCode, int proctoringId, int userId);

    /**
     * Returns a list of available TAs in the specified faculty who are not already assigned to a given proctoring.
     *
     * @param facultyId the faculty ID
     * @param proctoringId the class proctoring ID
     * @param userId the ID of the user making the request
     * @return a list of TA profiles
     */
    List<TAProfileDTO> getAllAvailableTAsByFacultyId(int facultyId, int proctoringId, int userId);

    /**
     * Returns a list of available TAs in the specified department,
     * filtered by eligibility and one-day restriction.
     *
     * @param departmentCode the department code
     * @param proctoringId the class proctoring ID
     * @param userId the ID of the user making the request
     * @param eligibilityRestriction whether to apply eligibility filtering
     * @param oneDayRestriction whether to apply one-day assignment restriction
     * @return a list of TA profiles
     */
    List<TAProfileDTO> getAllAvailableTAsByDepartmentCode(
            String departmentCode, int proctoringId, int userId, boolean eligibilityRestriction, boolean oneDayRestriction
    );

    /**
     * Returns a list of available TAs in the specified faculty,
     * filtered by eligibility and one-day restriction.
     *
     * @param facultyId the faculty ID
     * @param proctoringId the class proctoring ID
     * @param userId the ID of the user making the request
     * @param eligibilityRestriction whether to apply eligibility filtering
     * @param oneDayRestriction whether to apply one-day assignment restriction
     * @return a list of TA profiles
     */
    List<TAProfileDTO> getAllAvailableTAsByFacultyId(
            int facultyId, int proctoringId, int userId, boolean eligibilityRestriction, boolean oneDayRestriction
    );

    /**
     * Retrieves all TA profiles in the system.
     *
     * @return a list of TAProfileDTOs
     */
    List<TAProfileDTO> getAllTAProfiles();

    /**
     * Checks if the TA is currently enrolled in a specific course.
     *
     * @param taId the TA's ID
     * @param courseId the course ID
     * @return true if the TA is enrolled in the course
     */
    boolean doesTakeCourse(int taId, int courseId);

    /**
     * Checks if the TA is eligible to assist in a specific course.
     *
     * @param taId the TA's ID
     * @param courseId the course ID
     * @return true if the TA is eligible
     */
    boolean isTAEligible(int taId, int courseId);

    /**
     * Checks if the TA has no other proctoring assignments on the same day.
     *
     * @param taId the TA's ID
     * @param proctoringId the proctoring session to compare against
     * @return true if the TA has no conflicts
     */
    boolean noProctoringInOneDay(int taId, int proctoringId);
}