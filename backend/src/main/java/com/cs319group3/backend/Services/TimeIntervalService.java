package com.cs319group3.backend.Services;

import com.cs319group3.backend.DTOs.DateIntervalDTO;
import com.cs319group3.backend.DTOs.TimeIntervalDTO;

import java.util.List;

public interface TimeIntervalService {

    /**
     * Retrieves the schedule of a TA within the specified date interval.
     *
     * @param dateIntervalDTO the date range to query
     * @param id the ID of the TA
     * @return a list of TimeIntervalDTOs representing the TA's schedule
     */
    List<TimeIntervalDTO> getTAScheduleById(DateIntervalDTO dateIntervalDTO, int id);

    /**
     * Retrieves the schedule of an instructor within the specified date interval.
     *
     * @param dateIntervalDTO the date range to query
     * @param id the ID of the instructor
     * @return a list of TimeIntervalDTOs representing the instructor's schedule
     */
    List<TimeIntervalDTO> getInstructorScheduleById(DateIntervalDTO dateIntervalDTO, int id);

    // You can uncomment and use this if needed:
    // List<TimeIntervalDTO> getTATimeIntervalsByHour(LocalDateTime startDateTime, LocalDateTime endDateTime, int taId);
}