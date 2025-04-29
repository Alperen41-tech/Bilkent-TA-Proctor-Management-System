package com.cs319group3.backend.DTOs.RequestDTOs;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestDTO {

    private int requestId;
    private String requestType;
    private int senderId;
    private int receiverId;
    private LocalDateTime sentDateTime;
    private LocalDateTime responseDateTime;
    private Boolean isApproved;
    private String description;

    private String senderName;
    private String receiverName;

    private String receiverEmail;
    private String senderEmail;

    private String status;

    // authStaffProctoringRequest ~ taSwapRequest ~ instructorAdditionalTARequest

    private int classProctoringId;
    private String classProctoringEventName;
    private LocalDateTime classProctoringStartDate;
    private LocalDateTime classProctoringEndDate;


    // instructorAdditionalTARequest
    private int taCountNeeded;

    private Boolean isComplete;

    // taLeaveRequest

    private Boolean isUrgent;
    private LocalDateTime leaveStartDate;
    private LocalDateTime leaveEndDate;


    // workload request

    private String taskTypeName;
    private int timeSpent;
    private String courseCode;



}
