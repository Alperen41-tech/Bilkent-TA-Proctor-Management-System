package com.cs319group3.backend.Controllers.RequestControllers;

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

    @PostMapping("respond")
    public boolean respondToRequest(@RequestParam("id") int requestId, @RequestParam("response") boolean response) {
        return requestService.respondToRequest(requestId, response);
    }

    @GetMapping("getByReceiverId")
    public List<RequestDTO> getRequestsByReceiverId(@RequestParam("receiverId") int receiverId) {
        return requestService.getRequestsByReceiverUser(receiverId);
    }

    @GetMapping("getBySenderId")
    public List<RequestDTO> getRequestsBySenderId(@RequestParam("senderId") int senderId) {
        return requestService.getRequestsBySenderUser(senderId);
    }

}
