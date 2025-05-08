package com.cs319group3.backend.Controllers.RequestControllers;

import com.cs319group3.backend.Components.CurrentUserUtil;
import com.cs319group3.backend.DTOs.RequestDTOs.RequestDTO;
import com.cs319group3.backend.DTOs.TAProfileDTO;
import com.cs319group3.backend.Services.TASwapRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsible for managing TA swap requests and retrieving available TA profiles for class proctoring swaps.
 */
@RestController
@RequestMapping("swapRequest")
@ComponentScan(basePackages = {"com.cs319group3.backend.Controllers"})
@CrossOrigin(origins = "http://localhost:3000")
public class TASwapRequestController {

    @Autowired
    private TASwapRequestService swapRequestService;

    @Autowired
    private CurrentUserUtil currentUserUtil;

    /**
     * Creates a new TA swap request.
     *
     * @param requestDTO the request data submitted by the TA
     * @return ResponseEntity with true if request creation was successful
     */
    @PostMapping("createSwapRequest")
    public ResponseEntity<Boolean> createSwapRequest(@RequestBody RequestDTO requestDTO){
        return swapRequestService.createSwapRequest(requestDTO);
    }

    /**
     * Retrieves a list of available TA profiles who can swap for the given class proctoring.
     *
     * @param classProctoringId the ID of the class proctoring
     * @return a list of TAProfileDTOs available for swapping, or null if an exception occurs
     */
    @GetMapping("getAvailableTAProfilesForClassProctoring")
    public List<TAProfileDTO> getAvailableTAProfilesForClassProctoring(@RequestParam(name = "classProctoringId") int classProctoringId){
        try {
            int taId = currentUserUtil.getCurrentUserId();
            return swapRequestService.getAvailableTAProfilesForClassProctoring(classProctoringId, taId);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    // Future functionality (currently commented out):
    //
    // @GetMapping("getSentSwapRequests")
    // public ResponseEntity<List<RequestDTO>> getSwapRequestsBySender(@RequestParam(name = "id") int TAId) {
    //     return swapRequestService.getTASwapRequestsBySender(TAId);
    // }
    //
    // @GetMapping("getReceivedSwapRequests")
    // public ResponseEntity<List<RequestDTO>> getSwapRequestsByReceiver(@RequestParam(name = "id") int TAId) {
    //     return swapRequestService.getTASwapRequestsByReceiver(TAId);
    // }
}