package com.cs319group3.backend.Services;

import com.cs319group3.backend.DTOs.DateIntervalDTO;
import com.cs319group3.backend.DTOs.LogDTO;
import com.cs319group3.backend.Enums.LogType;

import java.time.LocalDateTime;
import java.util.List;

public interface LogService {
    void createLog(String message, LogType logType);
    List<LogDTO> getLogs(DateIntervalDTO dateIntervalDTO);
}
