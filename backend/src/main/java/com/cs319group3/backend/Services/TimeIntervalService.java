package com.cs319group3.backend.Services;

import com.cs319group3.backend.DTOs.DateIntervalDTO;
import com.cs319group3.backend.DTOs.TimeIntervalDTO;

import java.util.List;

public interface TimeIntervalService {
    public List<TimeIntervalDTO> getTAScheduleById(DateIntervalDTO dateIntervalDTO, int id);
    public List<TimeIntervalDTO> getInstructorScheduleById(DateIntervalDTO dateIntervalDTO, int id);
}
