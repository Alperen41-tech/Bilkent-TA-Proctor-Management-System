package com.cs319group3.backend.DTOMappers.RequestMappers;

import com.cs319group3.backend.DTOs.RequestDTOs.TAWorkloadRequestDTO;
import com.cs319group3.backend.Entities.RequestEntities.TAWorkloadRequest;
/*
public class TAWorkloadRequestMapper {

    public static TAWorkloadRequestDTO essentialMapper(TAWorkloadRequest taWorkloadRequest) {
        TAWorkloadRequestDTO dto = new TAWorkloadRequestDTO();
        dto.setTimeSpent(taWorkloadRequest.getTimeSpent());
        dto.setCourseCode(taWorkloadRequest.getCourse().getDepartmentCourseCode());
        dto.setDescription(taWorkloadRequest.getDescription());
        dto.setSentDate(taWorkloadRequest.getSentDate().toString());
        if ((taWorkloadRequest.getIsApproved()) != null && taWorkloadRequest.getIsApproved())
            dto.setResponseDate(taWorkloadRequest.getResponseDate().toString());
        dto.setTaskTypeName(taWorkloadRequest.getTaskType().getTaskTypeName());
        dto.setRequestId(taWorkloadRequest.getRequestId());
        dto.setTaMail(taWorkloadRequest.getSenderUser().getEmail());
        dto.setTaName(taWorkloadRequest.getSenderUser().getName() + " " + taWorkloadRequest.getSenderUser().getSurname());
        if ((taWorkloadRequest.getIsApproved()) != null && taWorkloadRequest.getIsApproved())
            dto.setStatus("accepted");
        if ((taWorkloadRequest.getIsApproved()) != null && !taWorkloadRequest.getIsApproved())
            dto.setStatus("rejected");
        return dto;
    }
}
*/