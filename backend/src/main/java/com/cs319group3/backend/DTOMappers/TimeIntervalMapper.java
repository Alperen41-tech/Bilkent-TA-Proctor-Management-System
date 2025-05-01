package com.cs319group3.backend.DTOMappers;

import com.cs319group3.backend.DTOs.TimeIntervalDTO;
import com.cs319group3.backend.Entities.TimeInterval;

import java.util.Locale;

public class TimeIntervalMapper {
    public static TimeIntervalDTO essentialMapper(TimeInterval timeInterval, String eventType, String eventName) {
        TimeIntervalDTO dto = new TimeIntervalDTO();
        dto.setEventType(eventType);
        dto.setEventName(eventName);
        String day = timeInterval.getDay();
        day = day.toLowerCase(Locale.ENGLISH);
        day = day.substring(0, 1).toUpperCase() + day.substring(1);
        dto.setDayOfWeek(day);
        String start = timeInterval.getStartTime().toString();
        String end = timeInterval.getEndTime().toString();
        dto.setStartTime(start);
        dto.setEndTime(end);
        return dto;
    }
}
