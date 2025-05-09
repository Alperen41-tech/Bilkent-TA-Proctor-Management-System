package com.cs319group3.backend.Services;

import com.cs319group3.backend.DTOs.ProctoringApplicationDTO;
import com.cs319group3.backend.DTOs.RequestDTOs.RequestDTO;
import com.cs319group3.backend.Entities.RequestEntities.Request;

import java.util.List;

/**
 * Service interface for managing general request operations such as responding to,
 * deleting, and fetching requests, as well as creating requests related to proctoring applications.
 */
public interface RequestService {

    /**
     * Updates the approval status of a request by its ID.
     *
     * @param requestId the ID of the request
     * @param response true if the request is approved, false if rejected
     * @return true if the operation was successful
     */
    boolean respondToRequest(int requestId, boolean response);

    /**
     * Deletes a request by its ID.
     *
     * @param requestId the ID of the request
     * @return true if the request was successfully deleted
     */
    boolean deleteRequest(int requestId);

    /**
     * Retrieves all requests where the given user is the receiver.
     *
     * @param userId the ID of the receiver
     * @return a list of RequestDTOs sent to the user
     */
    List<RequestDTO> getRequestsByReceiverUser(int userId);

    /**
     * Retrieves all requests sent by the given user.
     *
     * @param userId the ID of the sender
     * @return a list of RequestDTOs sent by the user
     */
    List<RequestDTO> getRequestsBySenderUser(int userId);

    /**
     * Creates a request based on a proctoring application.
     *
     * @param proctoringApplicationDTO the data for the proctoring application
     * @param deansOfficeId the ID of the Dean's Office creating the request
     * @return the created Request entity
     */
    Request createProctoringApplicationRequest(ProctoringApplicationDTO proctoringApplicationDTO, int deansOfficeId);
}