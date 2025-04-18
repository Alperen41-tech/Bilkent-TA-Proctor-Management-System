package com.cs319group3.backend.Services.ServiceImpls;

import com.cs319group3.backend.DTOs.TAProfileDTO;
import com.cs319group3.backend.DTOMappers.TAProfileMapper;
import com.cs319group3.backend.Entities.UserEntities.TA;
import com.cs319group3.backend.Repositories.TARepo;
import com.cs319group3.backend.Services.TAProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TAProfileServiceImpl implements TAProfileService {

    @Autowired
    private TARepo taRepository;

    @Override
    public TAProfileDTO getTAProfileById(int id) {
        Optional<TA> optionalTA = taRepository.findByUserId(id);

        if (optionalTA.isEmpty()) {
            throw new RuntimeException("TA with ID " + id + " not found.");
        }

        return TAProfileMapper.essentialMapper(optionalTA.get());
    }
}