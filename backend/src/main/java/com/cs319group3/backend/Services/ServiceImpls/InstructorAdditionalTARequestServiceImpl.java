package com.cs319group3.backend.Services.ServiceImpls;

import com.cs319group3.backend.DTOMappers.InstructorAdditionalTARequestMapper;
import com.cs319group3.backend.DTOs.InstructorAdditionalTARequestDTO;
import com.cs319group3.backend.Entities.RequestEntities.InstructorAdditionalTARequest;
import com.cs319group3.backend.Repositories.InstructorAdditionalTARequestRepo;
import com.cs319group3.backend.Services.InstructorAdditionalTARequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Service
public class InstructorAdditionalTARequestServiceImpl implements InstructorAdditionalTARequestService {
    @Autowired
    InstructorAdditionalTARequestRepo instructorAdditionalTARequestRepo;
    @Override
    public List<InstructorAdditionalTARequestDTO> getInstructorAdditionalTARequests(@RequestParam int receiverId){
        List<InstructorAdditionalTARequest> list = instructorAdditionalTARequestRepo.findAllByReceiverUser_UserId(receiverId);
        List<InstructorAdditionalTARequestDTO> dtos = new ArrayList<>();
        for (InstructorAdditionalTARequest instructorAdditionalTARequest : list) {
            dtos.add(InstructorAdditionalTARequestMapper.toDTO(instructorAdditionalTARequest));
        }
        return dtos;
    }
}
