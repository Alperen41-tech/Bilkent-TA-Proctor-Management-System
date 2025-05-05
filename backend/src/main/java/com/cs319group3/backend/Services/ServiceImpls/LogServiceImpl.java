package com.cs319group3.backend.Services.ServiceImpls;

import com.cs319group3.backend.DTOMappers.LogMapper;
import com.cs319group3.backend.DTOs.DateIntervalDTO;
import com.cs319group3.backend.DTOs.LogDTO;
import com.cs319group3.backend.Entities.Log;
import com.cs319group3.backend.Enums.LogType;
import com.cs319group3.backend.Repositories.LogRepo;
import com.cs319group3.backend.Services.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private final LogRepo logRepo;

    public LogServiceImpl(LogRepo logRepo) {
        this.logRepo = logRepo;
    }

    @Override
    public void createLog(String message, LogType logType) {
        Log log = new Log();
        log.setMessage(message);
        log.setLogType(logType);
        log.setLogDate(LocalDateTime.now());
        logRepo.save(log);
    }

    @Override
    public List<LogDTO> getLogs(DateIntervalDTO dateIntervalDTO) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime from = LocalDateTime.parse(dateIntervalDTO.getStartDate(), formatter);
        LocalDateTime to = LocalDateTime.parse(dateIntervalDTO.getEndDate(), formatter);
        List<Log> logs = logRepo.getLogsByLogDateBetween(from, to);
        List<LogDTO> dtos = new ArrayList<>();
        for (Log log : logs) {
            dtos.add(LogMapper.essentialMapper(log));
        }
        return dtos;
    }


}
