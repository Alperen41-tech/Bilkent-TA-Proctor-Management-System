package com.cs319group3.backend.Services;


import com.cs319group3.backend.DTOs.RequestDTOs.RequestDTO;
import com.cs319group3.backend.DTOs.TAProfileDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TASwapRequestService {


    /**
     * @param TAId that is the current ta we want to get his/her received request
     * @return list of request in terms of DTO
     */
    public ResponseEntity<List<RequestDTO>> getTASwapRequestsByReceiver(int TAId);


    /**
     * @param TAId that is the current ta we want to get his/her sent request
     * @return list of request in terms of DTO
     */
    public ResponseEntity<List<RequestDTO>> getTASwapRequestsBySender(int TAId);

    /**
     * get the classProctoringId and taId in order to create the swap request
     * @param requestDTO
     * @return
     */
    public ResponseEntity<Boolean> createSwapRequest(RequestDTO requestDTO);


    /**
     *
     * @param requestId take the request id and mark it as accepted
     * @return true if successfully handled
     */
    public ResponseEntity<Boolean> acceptSwapRequest(int requestId) throws Exception;


    /**
     *
     * @param classProctoringId  get this in order to decide the current class proctoring
     * @param taId  get this to decide which ta which is going to send the swap reqeust
     * @return
     */
    public List<TAProfileDTO> getAvailableTAProfilesForClassProctoring(int classProctoringId, int taId) throws Exception;


}
