package com.cs319group3.backend.DTOMappers;

import com.cs319group3.backend.DTOs.LogDTO;
import com.cs319group3.backend.Entities.Log;

public class LogMapper {
    public static LogDTO essentialMapper(Log log) {
        LogDTO dto = new LogDTO();
        dto.setMessage(log.getMessage());
        dto.setLogType(log.getLogType().toString());
        dto.setLogDate(log.getLogDate());
        return dto;
    }
}
