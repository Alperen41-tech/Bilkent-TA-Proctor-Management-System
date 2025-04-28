package com.cs319group3.backend.Services;


import com.cs319group3.backend.DTOs.RequestDTOs.TASwapRequestDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TASwapRequestService {


    /**
     * @param TAId that is the current ta we want to get his/her received request
     * @return list of request in terms of DTO
     */
    public ResponseEntity<List<TASwapRequestDTO>> getTASwapRequestsByReceiver(int TAId);


    /**
     * @param TAId that is the current ta we want to get his/her sent request
     * @return list of request in terms of DTO
     */
    public ResponseEntity<List<TASwapRequestDTO>> getTASwapRequestsBySender(int TAId);

    /**
     *
     * @param swapRequestRecieved takes the value to create swap reqeust accoringly
     * @return ResponseEntity to indicate whether the request is successfull
     */
    public ResponseEntity<Boolean> createSwapRequest(TASwapRequestDTO swapRequestRecieved);


    /**
     *
     * @param requestId take the request id and mark it as accepted
     * @return true if successfully handled
     */
    public ResponseEntity<Boolean> acceptSwapRequest(int requestId) throws Exception;




}
