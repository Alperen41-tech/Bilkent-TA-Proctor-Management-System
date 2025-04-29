package com.cs319group3.backend.DTOMappers.RequestMappers;

import com.cs319group3.backend.DTOs.RequestDTOs.InstructorAdditionalTARequestDTO;
import com.cs319group3.backend.DTOs.RequestDTOs.RequestDTO;
import com.cs319group3.backend.Entities.RequestEntities.InstructorAdditionalTARequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/*
public class InstructorAdditionalTARequestMapper {
    public static InstructorAdditionalTARequestDTO toDTO(InstructorAdditionalTARequest instructorAdditionalTARequest) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        InstructorAdditionalTARequestDTO requestDTO = new InstructorAdditionalTARequestDTO();
        RequestDTO partialDTO = new RequestDTO();
        partialDTO.setRequestId(instructorAdditionalTARequest.getRequestId());
        partialDTO.setReceiverId(instructorAdditionalTARequest.getReceiverUser().getUserId());
        partialDTO.setSenderId(instructorAdditionalTARequest.getSenderUser().getUserId());
        partialDTO.setSentDateTime(instructorAdditionalTARequest.getSentDate().format(formatter));
        partialDTO.setIsApproved(instructorAdditionalTARequest.getIsApproved());
        partialDTO.setDescription(instructorAdditionalTARequest.getDescription());
        LocalDateTime ldt = instructorAdditionalTARequest.getResponseDate();
        if (ldt != null) {
            partialDTO.setResponseDateTime(ldt.format(formatter));
        }
        else{
            partialDTO.setResponseDateTime("");
        }
        requestDTO.setRequestDTO(partialDTO);
        requestDTO.setComplete(instructorAdditionalTARequest.isComplete());
        requestDTO.setTaCount(instructorAdditionalTARequest.getTaCount());
        requestDTO.setClassProctoringId(instructorAdditionalTARequest.getClassProctoring().getClassProctoringId());
        return requestDTO;
    }
}*/
