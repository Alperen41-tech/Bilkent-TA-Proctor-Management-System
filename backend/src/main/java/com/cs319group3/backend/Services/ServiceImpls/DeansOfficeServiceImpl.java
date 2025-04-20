package com.cs319group3.backend.Services.ServiceImpls;

import com.cs319group3.backend.DTOMappers.DeansOfficeProfileMapper;
import com.cs319group3.backend.DTOs.DeansOfficeProfileDTO;
import com.cs319group3.backend.Entities.UserEntities.DeansOffice;
import com.cs319group3.backend.Repositories.DeansOfficeRepo;
import com.cs319group3.backend.Services.DeansOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeansOfficeServiceImpl implements DeansOfficeService {

    @Autowired
    private DeansOfficeRepo deansOfficeRepo;

    @Override
    public DeansOfficeProfileDTO getDeansOfficeProfileById(int id) {
        Optional<DeansOffice> deansOffice = deansOfficeRepo.findByUserId(id);
        if (!deansOffice.isPresent()) {
            throw new RuntimeException("DeansOffice with ID " + id + " not found.");
        }
        DeansOffice deansOfficeEntity = deansOffice.get();

        return DeansOfficeProfileMapper.essentialMapper(deansOfficeEntity);
    }
}
