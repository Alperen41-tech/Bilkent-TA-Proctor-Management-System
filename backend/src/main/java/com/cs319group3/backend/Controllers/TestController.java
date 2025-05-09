package com.cs319group3.backend.Controllers;

import com.cs319group3.backend.DTOs.DateIntervalDTO;
import com.cs319group3.backend.DTOs.TimeIntervalDTO;
import com.cs319group3.backend.Entities.ClassProctoring;
import com.cs319group3.backend.Entities.UserEntities.TA;
import com.cs319group3.backend.Repositories.ClassProctoringRepo;
import com.cs319group3.backend.Repositories.TARepo;
import com.cs319group3.backend.Services.ProctoringApplicationTARelationService;
import com.cs319group3.backend.Services.TAAvailabilityService;
import com.cs319group3.backend.Services.TAService;
import com.cs319group3.backend.Services.TimeIntervalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Controller for temporary testing of TA-related queries such as availability,
 * eligibility, course enrollments, and schedules.
 */
@RestController
@RequestMapping("test")
public class TestController {

    @Autowired
    private TAAvailabilityService taAvailabilityService;

    @Autowired
    private TimeIntervalService timeIntervalService;

    @Autowired
    private TARepo taRepo;

    @Autowired
    private ClassProctoringRepo classProctoringRepo;

    @Autowired
    private ProctoringApplicationTARelationService proctoringApplicationTARelationService;

    @Autowired
    private TAService taService;

    /**
     * Returns the schedule (as time intervals) for the TA with the specified Bilkent ID within a date range.
     *
     * @param bilkentId the TA's Bilkent ID
     * @param start the start date in string format
     * @param end the end date in string format
     * @return list of time intervals scheduled for the TA
     */
    @GetMapping("getSchedule")
    public List<TimeIntervalDTO> getSchedule(@RequestParam("bilkentId") String bilkentId,
                                             @RequestParam("start") String start,
                                             @RequestParam("end") String end) {

        Optional<TA> ta = taRepo.findByBilkentId(bilkentId);

        DateIntervalDTO date = new DateIntervalDTO();
        date.setStartDate(start);
        date.setEndDate(end);

        return timeIntervalService.getTAScheduleById(date, ta.get().getUserId());
    }

    /**
     * Checks if a TA is available between a given time range.
     *
     * @param bilkentId the TA's Bilkent ID
     * @param start the start datetime
     * @param end the end datetime
     * @return true if the TA is available
     */
    @GetMapping("isAvailable")
    public boolean isTAAvailable(@RequestParam("bilkentId") String bilkentId,
                                 @RequestParam("start") LocalDateTime start,
                                 @RequestParam("end") LocalDateTime end) {

        Optional<TA> ta = taRepo.findByBilkentId(bilkentId);
        ClassProctoring c = new ClassProctoring();
        c.setStartDate(start);
        c.setEndDate(end);
        return taAvailabilityService.isTAAvailable(ta.get(), c);
    }

    /**
     * Checks if a TA is eligible to assist with a specific course.
     *
     * @param taId the TA's user ID
     * @param courseId the course ID
     * @return true if the TA is eligible
     */
    @GetMapping("isTAEligible")
    public boolean isTAEligible(@RequestParam int taId, @RequestParam int courseId) {
        return taService.isTAEligible(taId, courseId);
    }

    /**
     * Checks if a TA is currently taking a specific course.
     *
     * @param taId the TA's user ID
     * @param courseId the course ID
     * @return true if the TA is enrolled in the course
     */
    @GetMapping("doesTakeCourse")
    public boolean doesTakeCourse(@RequestParam int taId, @RequestParam int courseId) {
        return taService.doesTakeCourse(taId, courseId);
    }
}