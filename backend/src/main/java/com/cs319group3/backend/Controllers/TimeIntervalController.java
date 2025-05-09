package com.cs319group3.backend.Controllers;

import com.cs319group3.backend.Components.CurrentUserUtil;
import com.cs319group3.backend.DTOs.DateIntervalDTO;
import com.cs319group3.backend.DTOs.TimeIntervalDTO;
import com.cs319group3.backend.Services.TimeIntervalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsible for retrieving schedule-related time intervals
 * for both TAs and instructors within specified date ranges.
 */
@RestController
@RequestMapping("timeInterval")
@CrossOrigin(origins = "http://localhost:3000")
public class TimeIntervalController {

    @Autowired
    private TimeIntervalService timeIntervalService;

    @Autowired
    private CurrentUserUtil currentUserUtil;

    /**
     * Retrieves the schedule of the currently logged-in TA within a given date interval.
     *
     * @param dateIntervalDTO the start and end dates
     * @return list of time intervals representing the TA's schedule
     */
    @PostMapping("taSchedule")
    public List<TimeIntervalDTO> getTASchedule(@RequestBody DateIntervalDTO dateIntervalDTO) {
        System.out.println("request received");
        int id = currentUserUtil.getCurrentUserId();
        return timeIntervalService.getTAScheduleById(dateIntervalDTO, id);
    }

    /**
     * Retrieves the schedule of a specific TA (by ID) within a given date interval.
     * Intended for instructor views of their TAs' schedules.
     *
     * @param dateIntervalDTO the start and end dates
     * @param id the user ID of the TA
     * @return list of time intervals for that TA
     */
    @PostMapping("taScheduleInInstructor")
    public List<TimeIntervalDTO> getTAScheduleInInstructor(@RequestBody DateIntervalDTO dateIntervalDTO,
                                                           @RequestParam("id") int id) {
        System.out.println("request received");
        return timeIntervalService.getTAScheduleById(dateIntervalDTO, id);
    }

    /**
     * Retrieves the schedule of the currently logged-in instructor within a given date interval.
     *
     * @param dateIntervalDTO the date range
     * @return list of time intervals representing the instructor's schedule
     */
    @PostMapping("instructorSchedule")
    public List<TimeIntervalDTO> getInstructorSchedule(@RequestBody DateIntervalDTO dateIntervalDTO) {
        int id = currentUserUtil.getCurrentUserId();
        System.out.println("request received");
        return timeIntervalService.getInstructorScheduleById(dateIntervalDTO, id);
    }
}