package com.cs319group3.backend.Services;

import com.cs319group3.backend.DTOs.RequestDTOs.RequestDTO;

/**
 * Service interface for managing TA leave requests.
 */
public interface TALeaveRequestService {

    /**
     * Creates a TA leave request based on the provided request data and TA ID.
     *
     * @param taLeaveRequest the data of the leave request
     * @param taId the ID of the TA submitting the request
     * @return true if the request is successfully created, false otherwise
     */
    boolean createTALeaveRequest(RequestDTO taLeaveRequest, int taId);
}