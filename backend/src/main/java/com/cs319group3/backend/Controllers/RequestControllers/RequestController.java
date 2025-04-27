package com.cs319group3.backend.Controllers.RequestControllers;

import com.cs319group3.backend.Services.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

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

}
