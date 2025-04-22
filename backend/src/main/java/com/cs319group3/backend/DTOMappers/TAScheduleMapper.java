package com.cs319group3.backend.DTOMappers;

import com.cs319group3.backend.DTOs.TAScheduleDTO;
import com.cs319group3.backend.Entities.TimeInterval;

public class TAScheduleMapper {
    public static TAScheduleDTO essentialMapper(TimeInterval timeInterval, String eventName) {
        TAScheduleDTO dto = new TAScheduleDTO();
        dto.setEventName(eventName);
        String day = timeInterval.getDay();
        day = day.toLowerCase();
        day = day.substring(0, 1).toUpperCase() + day.substring(1);
        dto.setDayOfWeek(day);
        String start = timeInterval.getStartTime().toString();
        start = start.substring(0, start.length() - 3);
        String end = timeInterval.getEndTime().toString();
        end = end.substring(0, end.length() - 3);
        dto.setStartTime(start);
        dto.setEndTime(end);
        return dto;
    }
}
