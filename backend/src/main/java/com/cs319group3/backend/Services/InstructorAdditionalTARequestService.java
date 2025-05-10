package com.cs319group3.backend.Services;

import com.cs319group3.backend.DTOs.RequestDTOs.RequestDTO;
import java.util.List;

public interface InstructorAdditionalTARequestService {

    /**
     * Retrieves all approved instructor additional TA requests for the given receiver.
     *
     * @param receiverId the ID of the receiver user
     * @return a list of approved RequestDTOs
     */
    List<RequestDTO> getApprovedInstructorAdditionalTARequests(int receiverId);

    /**
     * Retrieves all unapproved instructor additional TA requests for the given receiver.
     *
     * @param receiverId the ID of the receiver user
     * @return a list of unapproved RequestDTOs
     */
    List<RequestDTO> getUnapprovedInstructorAdditionalTARequests(int receiverId);

    /**
     * Creates a new instructor additional TA request.
     *
     * @param requestDTO the request data to be created
     * @param senderId   the ID of the sender user
     * @return true if the request was successfully created, false otherwise
     */
    boolean createInstructorAdditionalTARequest(RequestDTO requestDTO, int senderId) throws Exception;
}