package com.cs319group3.backend.Controllers.RequestControllers;


import com.cs319group3.backend.DTOs.RequestDTOs.RequestDTO;

import com.cs319group3.backend.Services.TASwapRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("swapRequest")
@ComponentScan(basePackages = {"com.cs319group3.backend.Controllers"})
@CrossOrigin(origins = "http://localhost:3000")
public class TASwapRequestController {


    @Autowired
    private TASwapRequestService swapRequestService;



    @GetMapping("getSentSwapRequests")
    public ResponseEntity<List<RequestDTO>> getSwapRequestsBySender(@RequestParam(name = "id") int TAId) {
        return swapRequestService.getTASwapRequestsBySender(TAId);
    }

    @GetMapping("getReceivedSwapRequests")
    public ResponseEntity<List<RequestDTO>> getSwapRequestsByReceiver(@RequestParam(name = "id") int TAId) {
        return swapRequestService.getTASwapRequestsByReceiver(TAId);
    }

    @PostMapping("createSwapRequest")
    public ResponseEntity<Boolean> createSwapRequest(@RequestBody RequestDTO swapRequestReceived){
        return swapRequestService.createSwapRequest(swapRequestReceived);
    }

    @PutMapping("acceptSwapRequest")
    public ResponseEntity<Boolean> acceptSwapRequest(@RequestParam(name = "id") int requestID){

        try {
            return swapRequestService.acceptSwapRequest(requestID);
        }
        catch (Exception e){
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }
}
