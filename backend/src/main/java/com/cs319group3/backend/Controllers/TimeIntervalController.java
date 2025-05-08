package com.cs319group3.backend.Controllers;

import com.cs319group3.backend.Components.CurrentUserUtil;
import com.cs319group3.backend.DTOs.DateIntervalDTO;
import com.cs319group3.backend.DTOs.TimeIntervalDTO;
import com.cs319group3.backend.Services.TimeIntervalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("timeInterval")
@ComponentScan(basePackages = {"com.cs319group3.backend.Controllers"})
@CrossOrigin(origins = "http://localhost:3000")
public class TimeIntervalController {

    @Autowired
    private TimeIntervalService timeIntervalService;
    @Autowired
    private CurrentUserUtil currentUserUtil;

    @PostMapping("taSchedule")
    public List<TimeIntervalDTO> getTASchedule(@RequestBody DateIntervalDTO dateIntervalDTO){
        System.out.println("request received");
        int id = currentUserUtil.getCurrentUserId();
        return timeIntervalService.getTAScheduleById(dateIntervalDTO, id);
    }

    @PostMapping("taScheduleInInstructor")
    public List<TimeIntervalDTO> getTAScheduleInInstructor(@RequestBody DateIntervalDTO dateIntervalDTO, @RequestParam("id") int id){
        System.out.println("request received");
        return timeIntervalService.getTAScheduleById(dateIntervalDTO, id);
    }

    @PostMapping("instructorSchedule")
    public List<TimeIntervalDTO> getInstructorSchedule(@RequestBody DateIntervalDTO dateIntervalDTO){
        int id = currentUserUtil.getCurrentUserId();
        System.out.println("request received");
        return timeIntervalService.getInstructorScheduleById(dateIntervalDTO, id);
    }
}
