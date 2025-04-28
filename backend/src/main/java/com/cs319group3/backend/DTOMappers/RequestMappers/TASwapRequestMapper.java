package com.cs319group3.backend.DTOMappers.RequestMappers;


import com.cs319group3.backend.DTOs.RequestDTO;
import com.cs319group3.backend.DTOs.RequestDTOs.TASwapRequestDTO;
import com.cs319group3.backend.Entities.ClassProctoring;
import com.cs319group3.backend.Entities.RequestEntities.TASwapRequest;
import com.cs319group3.backend.Entities.UserEntities.TA;
import com.cs319group3.backend.Repositories.ClassProctoringRepo;
import com.cs319group3.backend.Repositories.TARepo;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class TASwapRequestMapper {

    @Autowired
    TARepo taRepo;

    @Autowired
    ClassProctoringRepo classProctoringRepo;

    public TASwapRequest essentialEntityTo(TASwapRequestDTO swapRequestDTO) throws Exception {
        Optional<TA> senderTA = taRepo.findByUserId(swapRequestDTO.getRequest().getSenderId());
        Optional<TA> receiverTA = taRepo.findByUserId(swapRequestDTO.getRequest().getReceiverId());
        Optional<ClassProctoring> classProctoring = classProctoringRepo.findById(swapRequestDTO.getClassProctoringId());

        if (!senderTA.isPresent()) {
            throw new Exception("Sender TA cannot be found in database");
        }
        if (!receiverTA.isPresent()) {
            throw new Exception("Receiver TA cannot be found in database");
        }
        if (!classProctoring.isPresent()) {
            throw new Exception("ClassProctoring cannot be found in database");
        }

        TASwapRequest swapRequest = new TASwapRequest();

        // Setting Request (superclass) fields
        swapRequest.setSentDate(LocalDateTime.now()); // you can adjust how you set the time if needed
        swapRequest.setIsApproved(false);           // defaulting to false or null based on your design
        swapRequest.setDescription(swapRequestDTO.getRequest().getDescription()); // DTO has description
        swapRequest.setSenderUser(senderTA.get());
        swapRequest.setReceiverUser(receiverTA.get());
        swapRequest.setResponseDate(null);

        // Setting TASwapRequest-specific field
        swapRequest.setClassProctoring(classProctoring.get());

        return swapRequest;
    }


    public TASwapRequestDTO essentialMapper(TASwapRequest swapRequest) {
        TASwapRequestDTO swapRequestDTO = new TASwapRequestDTO();
        RequestDTO requestDTO = new RequestDTO();

        // Set Request base info
        requestDTO.setRequestId(swapRequest.getRequestId());
        swapRequestDTO.setRequest(requestDTO);

        // Set class proctoring info
        swapRequestDTO.setClassProctoringEventName(swapRequest.getClassProctoring().getEventName());
        swapRequestDTO.setProctoringStartDate(swapRequest.getClassProctoring().getStartDate());
        swapRequestDTO.setProctoringEndDate(swapRequest.getClassProctoring().getEndDate());

        // Set sender and receiver info
        swapRequestDTO.setSenderName(swapRequest.getSenderUser().getName());    // assuming User has getFullName()
        swapRequestDTO.setReceiverName(swapRequest.getReceiverUser().getName());

        swapRequestDTO.setSenderEmail(swapRequest.getSenderUser().getEmail());      // assuming User has getEmail()
        swapRequestDTO.setReceiverEmail(swapRequest.getReceiverUser().getEmail());

        // Set dates
        swapRequestDTO.setSentDate(swapRequest.getSentDate());
        swapRequestDTO.setResponseDate(swapRequest.getResponseDate());

        return swapRequestDTO;
    }

    public List<TASwapRequestDTO> essentialMapper(List<TASwapRequest> swapRequests) {
        List<TASwapRequestDTO> swapRequestDTOs = new ArrayList<>();

        for (TASwapRequest swapRequest : swapRequests) {
            swapRequestDTOs.add(essentialMapper(swapRequest));
        }
        return swapRequestDTOs;
    }



}
