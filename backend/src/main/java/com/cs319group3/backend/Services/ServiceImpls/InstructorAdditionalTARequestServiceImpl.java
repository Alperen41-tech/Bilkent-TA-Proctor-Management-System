package com.cs319group3.backend.Services.ServiceImpls;

import com.cs319group3.backend.DTOMappers.RequestMappers.RequestMapper;

import com.cs319group3.backend.DTOs.RequestDTOs.RequestDTO;
import com.cs319group3.backend.Entities.RequestEntities.InstructorAdditionalTARequest;
import com.cs319group3.backend.Repositories.InstructorAdditionalTARequestRepo;
import com.cs319group3.backend.Services.InstructorAdditionalTARequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Service
public class InstructorAdditionalTARequestServiceImpl implements InstructorAdditionalTARequestService {
    @Autowired
    InstructorAdditionalTARequestRepo instructorAdditionalTARequestRepo;
    @Override
    public List<RequestDTO> getInstructorAdditionalTARequests(@RequestParam int receiverId){
        List<InstructorAdditionalTARequest> list = instructorAdditionalTARequestRepo.findByReceiverUser_UserId(receiverId);
        List<RequestDTO> dtos = new ArrayList<>();
        for (InstructorAdditionalTARequest instructorAdditionalTARequest : list) {
            dtos.add(RequestMapper.instructorAdditionalTARequestMapper(instructorAdditionalTARequest));
        }
        return dtos;
    }
}
