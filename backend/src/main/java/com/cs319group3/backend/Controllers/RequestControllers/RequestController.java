package com.cs319group3.backend.Controllers.RequestControllers;

import com.cs319group3.backend.Components.CurrentUserUtil;
import com.cs319group3.backend.DTOs.RequestDTOs.RequestDTO;
import com.cs319group3.backend.Services.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("request")
@ComponentScan(basePackages = {"com.cs319group3.backend.Controllers"})
@CrossOrigin(origins = "http://localhost:3000")
public class RequestController {

    @Autowired
    private RequestService requestService;

    @Autowired
    private CurrentUserUtil currentUserUtil;

    /**
     * Responds to a request with the specified approval decision.
     *
     * @param requestId the ID of the request
     * @param response true if approved, false if rejected
     * @return true if the response was successfully processed
     */
    @PutMapping("respond")
    public boolean respondToRequest(@RequestParam("id") int requestId, @RequestParam("response") boolean response) throws Exception {
        return requestService.respondToRequest(requestId, response);
    }

    /**
     * Deletes the request with the specified ID.
     *
     * @param requestId the ID of the request to delete
     * @return true if the request was successfully deleted
     */
    @DeleteMapping("deleteRequest")
    public boolean deleteRequest(@RequestParam("id") int requestId) {
        return requestService.deleteRequest(requestId);
    }

    /**
     * Retrieves all requests received by the currently logged-in user.
     *
     * @return a list of RequestDTOs received by the user
     */
    @GetMapping("getByReceiverId")
    public List<RequestDTO> getRequestsByReceiverId() {
        int receiverId = currentUserUtil.getCurrentUserId();
        return requestService.getRequestsByReceiverUser(receiverId);
    }

    /**
     * Retrieves all requests sent by the currently logged-in user.
     *
     * @return a list of RequestDTOs sent by the user
     */
    @GetMapping("getBySenderId")
    public List<RequestDTO> getRequestsBySenderId() {
        int senderId = currentUserUtil.getCurrentUserId();
        return requestService.getRequestsBySenderUser(senderId);
    }

}