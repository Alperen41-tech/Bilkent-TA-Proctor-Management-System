package com.cs319group3.backend.Services;

import com.cs319group3.backend.DTOs.TAProfileDTO;
import com.cs319group3.backend.Entities.ClassProctoring;
import com.cs319group3.backend.Entities.UserEntities.TA;

import java.util.List;

/**
 * Service interface for handling authorization-based TA proctoring requests,
 * including sending, validating, and automatically managing requests.
 */
public interface AuthStaffProctoringRequestService {

    /**
     * Checks if a request from the sender to the receiver for the given class proctoring already exists.
     *
     * @param receiverId the ID of the TA (receiver)
     * @param classProctoringId the ID of the class proctoring session
     * @return true if a request has already been sent
     */
    boolean isRequestAlreadySent(int receiverId, int classProctoringId);

    /**
     * Sends a proctoring request to a TA.
     *
     * @param classProctoringId the ID of the class proctoring session
     * @param taId the ID of the TA to whom the request is sent
     * @param senderId the ID of the user sending the request
     * @param isApproved true if the request is pre-approved (forced)
     * @return true if the request was successfully sent
     */
    boolean sendAuthStaffProctoringRequest(int classProctoringId, int taId, int senderId, boolean isApproved);

    /**
     * Sends multiple proctoring requests to a list of TAs.
     *
     * @param dtoList the list of TA profile DTOs representing TAs to be requested
     * @param classProctoringId the ID of the class proctoring session
     * @param senderId the ID of the sender
     * @param isApproved true if these are forced requests
     * @return true if all requests were successfully sent
     */
    int sendAuthStaffProctoringRequests(List<TAProfileDTO> dtoList, int classProctoringId, int senderId, boolean isApproved);

    /**
     * Checks if an unforced request can be sent for the given class proctoring.
     *
     * @param classProctoringId the ID of the class proctoring
     * @return true if unforced requests are allowed
     */
    boolean canUnforcedRequestBeSent(int classProctoringId);

    /**
     * Checks if a forced request can be sent for the given class proctoring.
     *
     * @param classProctoringId the ID of the class proctoring
     * @return true if forced requests are allowed
     */
    boolean canForcedRequestBeSent(int classProctoringId);

    /**
     * Checks if a proctoring request can still be accepted for the given session.
     *
     * @param classProctoringId the ID of the class proctoring
     * @return true if acceptance is allowed
     */
    boolean canRequestBeAccepted(int classProctoringId);

    /**
     * Automatically sends TA proctoring requests to available candidates in a department.
     *
     * @param classProctoringId the proctoring session ID
     * @param departmentCode the department code
     * @param senderId the ID of the sender
     * @param count number of TAs needed
     * @param eligibilityRestriction if true, filter by course eligibility
     * @param oneDayRestriction if true, enforce one-proctoring-per-day limit
     * @return list of TA profiles who received the request
     */
    List<TAProfileDTO> sendAuthStaffProctoringRequestAutomaticallyInDepartment(
            int classProctoringId, String departmentCode, int senderId, int count, boolean eligibilityRestriction, boolean oneDayRestriction
    );

    /**
     * Automatically sends TA proctoring requests to available candidates in a faculty.
     *
     * @param classProctoringId the proctoring session ID
     * @param facultyId the faculty ID
     * @param senderId the ID of the sender
     * @param count number of TAs needed
     * @param eligibilityRestriction if true, filter by course eligibility
     * @param oneDayRestriction if true, enforce one-proctoring-per-day limit
     * @return list of TA profiles who received the request
     */
    List<TAProfileDTO> sendAuthStaffProctoringRequestAutomaticallyInFaculty(
            int classProctoringId, int facultyId, int senderId, int count, boolean eligibilityRestriction, boolean oneDayRestriction
    );

    /**
     * Rejects all remaining pending requests for a class proctoring session
     * if the required TA count has been met.
     *
     * @param classProctoringId the ID of the class proctoring session
     */
    void rejectRequestsIfNeeded(int classProctoringId);
}