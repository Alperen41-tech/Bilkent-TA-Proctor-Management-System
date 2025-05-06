package com.cs319group3.backend.Controllers;

import com.cs319group3.backend.DTOs.DateIntervalDTO;
import com.cs319group3.backend.DTOs.TimeIntervalDTO;
import com.cs319group3.backend.Entities.ClassProctoring;
import com.cs319group3.backend.Entities.TimeInterval;
import com.cs319group3.backend.Entities.UserEntities.TA;
import com.cs319group3.backend.Repositories.ClassProctoringRepo;
import com.cs319group3.backend.Repositories.TARepo;
import com.cs319group3.backend.Services.TAAvailabilityService;
import com.cs319group3.backend.Services.TimeIntervalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("test")
public class TestController {

    @Autowired
    TAAvailabilityService taAvailabilityService;

    @Autowired
    TimeIntervalService timeIntervalService;

    @Autowired
    TARepo taRepo;

    @Autowired
    ClassProctoringRepo classProctoringRepo;

    @GetMapping("getSchedule")
    public List<TimeIntervalDTO> getSchedule(@RequestParam("bilkentId") String bilkentId, @RequestParam("start") String start, @RequestParam("end") String end){

        Optional<TA> ta = taRepo.findByBilkentId(bilkentId);

        DateIntervalDTO date = new DateIntervalDTO();
        date.setStartDate(start);
        date.setEndDate(end);

        return timeIntervalService.getTAScheduleById(date, ta.get().getUserId());
    }

    @GetMapping("isAvailable")
    public boolean isTAAvailable(@RequestParam("bilkentId") String bilkentId, LocalDateTime start, LocalDateTime end){

        Optional<TA> ta = taRepo.findByBilkentId(bilkentId);
        ClassProctoring c = new ClassProctoring();
        c.setStartDate(start);
        c.setEndDate(end);
        return taAvailabilityService.isTAAvailable(ta.get(), c);
    }
}
