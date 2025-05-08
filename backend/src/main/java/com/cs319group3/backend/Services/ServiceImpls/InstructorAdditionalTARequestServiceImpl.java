package com.cs319group3.backend.Services.ServiceImpls;

import com.cs319group3.backend.DTOMappers.RequestMappers.RequestMapper;

import com.cs319group3.backend.DTOs.RequestDTOs.RequestDTO;
import com.cs319group3.backend.Entities.Department;
import com.cs319group3.backend.Entities.Faculty;
import com.cs319group3.backend.Entities.RequestEntities.InstructorAdditionalTARequest;
import com.cs319group3.backend.Entities.RequestEntities.Request;
import com.cs319group3.backend.Entities.UserEntities.DeansOffice;
import com.cs319group3.backend.Entities.UserEntities.Instructor;
import com.cs319group3.backend.Repositories.InstructorAdditionalTARequestRepo;
import com.cs319group3.backend.Repositories.InstructorRepo;
import com.cs319group3.backend.Repositories.RequestRepo;
import com.cs319group3.backend.Services.InstructorAdditionalTARequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InstructorAdditionalTARequestServiceImpl implements InstructorAdditionalTARequestService {
    @Autowired
    private InstructorAdditionalTARequestRepo instructorAdditionalTARequestRepo;

    @Autowired
    private RequestMapper requestMapper;
    @Autowired
    private RequestRepo requestRepo;
    @Autowired
    private InstructorRepo instructorRepo;


    @Override
    public List<RequestDTO> getApprovedInstructorAdditionalTARequests(@RequestParam int receiverId){
        List<InstructorAdditionalTARequest> list = instructorAdditionalTARequestRepo.findByReceiverIdAndIsApprovedTrue(receiverId);
        List<RequestDTO> dtos = new ArrayList<>();
        for (InstructorAdditionalTARequest instructorAdditionalTARequest : list) {
            dtos.add(requestMapper.instructorAdditionalTARequestMapper(instructorAdditionalTARequest));
        }
        return dtos;
    }

    @Override
    public List<RequestDTO> getUnapprovedInstructorAdditionalTARequests(@RequestParam int receiverId){
        List<InstructorAdditionalTARequest> list = instructorAdditionalTARequestRepo.findByReceiverIdAndIsApprovedFalseAndResponseDateNull(receiverId);
        List<RequestDTO> dtos = new ArrayList<>();
        for (InstructorAdditionalTARequest instructorAdditionalTARequest : list) {
            dtos.add(requestMapper.instructorAdditionalTARequestMapper(instructorAdditionalTARequest));
        }
        return dtos;
    }

    @Override
    public boolean createInstructorAdditionalTARequest(RequestDTO requestDTO, int senderId) {

        requestDTO.setSenderId(senderId);

        Optional<Instructor> instructor = instructorRepo.findById(senderId);
        if (!instructor.isPresent()) {
            throw new RuntimeException("Instructor not found, something went wrong");
        }

        DeansOffice deansOffice = instructor.get().getDepartment().getFaculty().getDeansOffice();

        InstructorAdditionalTARequest request = requestMapper.instructorAdditionalTARequestToEntityMapper(requestDTO);
        request.setReceiverUser(deansOffice);
        instructorAdditionalTARequestRepo.save(request);
        return true;

    }
}
