package com.cs319group3.backend.Services;


import com.cs319group3.backend.DTOs.RequestDTOs.TASwapRequestDTO;
import org.springframework.http.ResponseEntity;

public interface TASwapRequestService {


    /**
     *
     * @param swapRequestRecieved takes the value to create swap reqeust accoringly
     * @return ResponseEntity to indicate whether or not the request is successfull
     */
    public ResponseEntity<Boolean> createSwapRequest(TASwapRequestDTO swapRequestRecieved);




}
