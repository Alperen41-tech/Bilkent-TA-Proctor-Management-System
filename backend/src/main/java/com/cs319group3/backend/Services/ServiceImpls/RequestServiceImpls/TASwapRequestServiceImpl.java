package com.cs319group3.backend.Services.ServiceImpls.RequestServiceImpls;


import com.cs319group3.backend.DTOMappers.RequestMappers.TASwapRequestMapper;
import com.cs319group3.backend.DTOs.RequestDTOs.TASwapRequestDTO;
import com.cs319group3.backend.Entities.RequestEntities.TASwapRequest;
import com.cs319group3.backend.Repositories.TASwapRequestRepo;
import com.cs319group3.backend.Services.TASwapRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class TASwapRequestServiceImpl implements TASwapRequestService {

    @Autowired
    TASwapRequestMapper taswapRequestMapper;
    @Autowired
    TASwapRequestRepo taswapRequestRepo;

    @Override
    public ResponseEntity<Boolean> createSwapRequest(TASwapRequestDTO swapRequestReceived) {
        try {
            TASwapRequest swapRequest = taswapRequestMapper.essentialEntityTo(swapRequestReceived);
            taswapRequestRepo.save(swapRequest);

            return ResponseEntity.ok(true);
        } catch (Exception e) {
            e.printStackTrace(); // for debugging, or better, log it properly
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }
    }




}

