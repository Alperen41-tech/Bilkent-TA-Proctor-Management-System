package com.cs319group3.backend.Controllers;

import com.cs319group3.backend.DTOs.DateIntervalDTO;
import com.cs319group3.backend.DTOs.LogDTO;
import com.cs319group3.backend.Services.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsible for retrieving log records based on date intervals.
 */
@RestController
@RequestMapping("log")
@CrossOrigin(origins = "http://localhost:3000")
public class LogController {

    @Autowired
    private LogService logService;

    /**
     * Retrieves logs within the specified date interval.
     *
     * @param dateIntervalDTO the start and end date to filter logs
     * @return a list of LogDTOs within the specified interval
     */
    @PostMapping("getByDate")
    public List<LogDTO> getLogs(@RequestBody DateIntervalDTO dateIntervalDTO) {
        return logService.getLogs(dateIntervalDTO);
    }
}