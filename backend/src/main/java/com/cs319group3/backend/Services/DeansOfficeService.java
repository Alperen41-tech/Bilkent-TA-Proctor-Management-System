package com.cs319group3.backend.Services;

import com.cs319group3.backend.DTOs.CreateDeansOfficeDTO;
import com.cs319group3.backend.DTOs.DeansOfficeProfileDTO;
import com.cs319group3.backend.Entities.UserEntities.DeansOffice;

public interface DeansOfficeService {
    public DeansOfficeProfileDTO getDeansOfficeProfileById(int id);
    public boolean createDeansOffice(CreateDeansOfficeDTO dto);
}
