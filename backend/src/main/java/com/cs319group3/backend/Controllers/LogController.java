package com.cs319group3.backend.Controllers;

import com.cs319group3.backend.DTOs.DateIntervalDTO;
import com.cs319group3.backend.DTOs.LogDTO;
import com.cs319group3.backend.Services.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("log")
@ComponentScan(basePackages = {"com.cs319group3.backend.Controllers"})
@CrossOrigin(origins = "http://localhost:3000")
public class LogController {

    @Autowired
    LogService logService;

    @GetMapping("getByDate")
    List<LogDTO> getLogs(@RequestBody DateIntervalDTO dateIntervalDTO) {
        return logService.getLogs(dateIntervalDTO);
    }
}
