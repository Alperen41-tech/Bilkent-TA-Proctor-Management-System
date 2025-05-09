package com.cs319group3.backend.Services;

import com.cs319group3.backend.DTOs.DateIntervalDTO;
import com.cs319group3.backend.DTOs.LogDTO;
import com.cs319group3.backend.Enums.LogType;

import java.util.List;

/**
 * Provides logging functionality for system events.
 */
public interface LogService {

    /**
     * Creates a new log entry with the given message and type.
     *
     * @param message  the log message to record
     * @param logType  the type/category of the log
     */
    void createLog(String message, LogType logType);

    /**
     * Retrieves logs that fall within the specified date interval.
     *
     * @param dateIntervalDTO  the date range for filtering logs
     * @return a list of logs matching the date interval
     */
    List<LogDTO> getLogs(DateIntervalDTO dateIntervalDTO);
}