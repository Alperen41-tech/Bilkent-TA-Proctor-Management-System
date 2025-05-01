package com.cs319group3.backend.Controllers.RequestControllers;


import com.cs319group3.backend.DTOs.RequestDTOs.RequestDTO;

import com.cs319group3.backend.DTOs.TAProfileDTO;
import com.cs319group3.backend.Services.TASwapRequestService;
import org.apache.coyote.Response;
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



//    @GetMapping("getSentSwapRequests")
//    public ResponseEntity<List<RequestDTO>> getSwapRequestsBySender(@RequestParam(name = "id") int TAId) {
//        return swapRequestService.getTASwapRequestsBySender(TAId);
//    }
//
//    @GetMapping("getReceivedSwapRequests")
//    public ResponseEntity<List<RequestDTO>> getSwapRequestsByReceiver(@RequestParam(name = "id") int TAId) {
//        return swapRequestService.getTASwapRequestsByReceiver(TAId);
//    }

    @PostMapping("createSwapRequest")
    public ResponseEntity<Boolean> createSwapRequest(@RequestBody RequestDTO requestDTO){
        return swapRequestService.createSwapRequest(requestDTO);
    }


    @GetMapping("getAvailableTAProfilesForClassProctoring")
    public List<TAProfileDTO> getAvailableTAProfilesForClassProctoring(@RequestParam(name = "classProctoringId") int classProctoringId, @RequestParam(name = "taId") int taId){
        try {
            return swapRequestService.getAvailableTAProfilesForClassProctoring(classProctoringId, taId);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
