package com.cs319group3.backend.DTOMappers;

import com.cs319group3.backend.DTOs.TAWorkloadRequestDTO;
import com.cs319group3.backend.Entities.RequestEntities.TAWorkloadRequest;

public class TAWorkloadRequestMapper {

    public static TAWorkloadRequestDTO essentialMapper(TAWorkloadRequest taWorkloadRequest) {
        TAWorkloadRequestDTO dto = new TAWorkloadRequestDTO();
        dto.setTimeSpent(taWorkloadRequest.getTimeSpent());
        dto.setCourseCode(taWorkloadRequest.getCourse().getDepartmentCourseCode());
        dto.setDescription(taWorkloadRequest.getDescription());
        dto.setSentDate(taWorkloadRequest.getSentDate().toString());
        if (taWorkloadRequest.getIsApproved())
            dto.setResponseDate(taWorkloadRequest.getResponseDate().toString());
        dto.setTaskTypeName(taWorkloadRequest.getTaskType().getTaskTypeName());
        return dto;
    }
}
