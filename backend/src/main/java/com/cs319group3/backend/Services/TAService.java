package com.cs319group3.backend.Services;

import com.cs319group3.backend.DTOs.CreateTADTO;
import com.cs319group3.backend.DTOs.DateIntervalDTO;
import com.cs319group3.backend.DTOs.TAProfileDTO;
import com.cs319group3.backend.DTOs.TAScheduleDTO;

import java.time.LocalDate;
import java.util.List;

public interface TAService {
    public TAProfileDTO getTAProfileById(int id);
    public List<TAScheduleDTO> getTAScheduleById(DateIntervalDTO dateIntervalDTO, int id);
    public boolean createNewTA(CreateTADTO createTADTO);
}
