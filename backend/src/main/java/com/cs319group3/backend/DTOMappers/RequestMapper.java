package com.cs319group3.backend.DTOMappers;

import com.cs319group3.backend.DTOs.RequestDTO;
import com.cs319group3.backend.Entities.RequestEntities.Request;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RequestMapper {

    public static RequestDTO toDTO(Request request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setRequestId(request.getRequestId());
        requestDTO.setRequestType(request.getClass().getSimpleName());
        requestDTO.setIsApproved(request.getIsApproved());
        requestDTO.setDescription(request.getDescription());
        requestDTO.setReceiverId(request.getReceiverUser().getUserId());
        requestDTO.setSenderId(request.getSenderUser().getUserId());
        requestDTO.setSentDateTime(request.getSentDate().format(formatter));
        LocalDateTime ldt = request.getResponseDate();
        if (ldt != null) {
            requestDTO.setResponseDateTime(ldt.format(formatter));
        }
        else{
            requestDTO.setResponseDateTime("");
        }
        return requestDTO;
    }
}
