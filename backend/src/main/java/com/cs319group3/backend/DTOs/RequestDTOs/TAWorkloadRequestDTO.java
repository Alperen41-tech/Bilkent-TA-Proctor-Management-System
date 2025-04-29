package com.cs319group3.backend.DTOs.RequestDTOs;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TAWorkloadRequestDTO {
    private String taskTypeName;
    private int timeSpent;
    private int requestId;
    private String courseCode;
    private String description;
    private String sentDate;
    private String responseDate;
    private String status;
    private String taName;
    private String taMail;
}
