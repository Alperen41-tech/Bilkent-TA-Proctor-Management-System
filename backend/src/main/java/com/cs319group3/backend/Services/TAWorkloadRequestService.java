package com.cs319group3.backend.Services;

import com.cs319group3.backend.DTOs.RequestDTOs.RequestDTO;

import java.util.List;

public interface TAWorkloadRequestService {

    /**
     * Creates a TA workload request with the given request data and TA ID.
     *
     * @param dto   The request data transfer object.
     * @param taId  The ID of the TA creating the request.
     * @return      True if the request was created successfully, false otherwise.
     */
    boolean createTAWorkloadRequest(RequestDTO dto, int taId);

    /**
     * Retrieves all workload requests created by a specific TA.
     *
     * @param taId  The ID of the TA.
     * @return      A list of workload requests made by the TA.
     */
    List<RequestDTO> getTAWorkloadRequestsByTA(int taId);

    /**
     * Calculates the total approved workload time for a given TA.
     *
     * @param taId  The ID of the TA.
     * @return      The total workload time in minutes.
     */
    int getTotalWorkload(int taId);

    /**
     * Retrieves all workload requests received by a specific instructor.
     *
     * @param instructorId  The ID of the instructor.
     * @return              A list of workload requests addressed to the instructor.
     */
    List<RequestDTO> getTAWorkloadRequestsByInstructor(int instructorId);
}