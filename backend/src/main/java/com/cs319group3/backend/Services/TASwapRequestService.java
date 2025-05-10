package com.cs319group3.backend.Services;

import com.cs319group3.backend.DTOs.RequestDTOs.RequestDTO;
import com.cs319group3.backend.DTOs.TAProfileDTO;
import com.cs319group3.backend.Entities.ClassProctoring;
import com.cs319group3.backend.Entities.UserEntities.TA;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Service interface for handling TA swap requests between proctorings.
 */
public interface TASwapRequestService {

    /**
     * Retrieves all swap requests received by the given TA.
     *
     * @param TAId the ID of the TA receiving the swap requests
     * @return list of swap requests as DTOs wrapped in a ResponseEntity
     */
    ResponseEntity<List<RequestDTO>> getTASwapRequestsByReceiver(int TAId);

    /**
     * Retrieves all swap requests sent by the given TA.
     *
     * @param TAId the ID of the TA who sent the requests
     * @return list of swap requests as DTOs wrapped in a ResponseEntity
     */
    ResponseEntity<List<RequestDTO>> getTASwapRequestsBySender(int TAId);

    /**
     * Creates a swap request based on the provided request data.
     *
     * @param requestDTO the data of the swap request
     * @return ResponseEntity containing true if creation is successful
     */
    ResponseEntity<Boolean> createSwapRequest(RequestDTO requestDTO) throws Exception;

    /**
     * Accepts a swap request by marking it as approved and applying the swap logic.
     *
     * @param requestId the ID of the request to approve
     * @return true if successfully handled
     * @throws Exception if the request or TA is invalid
     */
    ResponseEntity<Boolean> acceptSwapRequest(int requestId) throws Exception;

    /**
     * Finds all available TA profiles for a given class proctoring that are eligible to receive swap requests.
     *
     * @param classProctoringId the ID of the class proctoring
     * @param taId the ID of the TA requesting the swap
     * @return list of available TA profiles
     * @throws Exception if an error occurs during availability check
     */
    List<TAProfileDTO> getAvailableTAProfilesForClassProctoring(int classProctoringId, int taId) throws Exception;

    /**
     * Checks whether a swap request has already been sent from the sender TA to the receiver TA for the given class proctoring.
     *
     * @param senderId the ID of the TA sending the request
     * @param receiver the TA who may have received the request
     * @param ctr the class proctoring involved
     * @return true if a request is already sent, false otherwise
     */
    boolean isRequestAlreadySent(int senderId, TA receiver, ClassProctoring ctr);
}