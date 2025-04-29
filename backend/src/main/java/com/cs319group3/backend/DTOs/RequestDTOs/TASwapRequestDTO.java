package com.cs319group3.backend.DTOs.RequestDTOs;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TASwapRequestDTO {


    private RequestDTO request;
    private int classProctoringId;

    private String classProctoringEventName;
    private String senderName;
    private String receiverName;
    private LocalDateTime sentDate;
    private LocalDateTime responseDate;

    private LocalDateTime proctoringStartDate;
    private LocalDateTime proctoringEndDate;
    private String receiverEmail;
    private String senderEmail;
}
