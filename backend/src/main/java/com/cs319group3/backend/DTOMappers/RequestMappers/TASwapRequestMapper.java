package com.cs319group3.backend.DTOMappers.RequestMappers;


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
import java.util.Optional;

@Component
public class TASwapRequestMapper {

    @Autowired
    TARepo taRepo;

    @Autowired
    ClassProctoringRepo classProctoringRepo;

    public TASwapRequest essentialEntityTo(TASwapRequestDTO swapRequestDTO) throws Exception {
        Optional<TA> senderTA = taRepo.findByUserId(swapRequestDTO.getSenderTAId());
        Optional<TA> receiverTA = taRepo.findByUserId(swapRequestDTO.getReceiverTAId());
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
        swapRequest.setDescription(swapRequestDTO.getDescription()); // DTO has description
        swapRequest.setSenderUser(senderTA.get());
        swapRequest.setReceiverUser(receiverTA.get());
        swapRequest.setResponseDate(null);

        // Setting TASwapRequest-specific field
        swapRequest.setClassProctoring(classProctoring.get());

        return swapRequest;
    }



}
