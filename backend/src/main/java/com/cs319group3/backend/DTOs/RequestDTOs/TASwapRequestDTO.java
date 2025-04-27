package com.cs319group3.backend.DTOs.RequestDTOs;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TASwapRequestDTO {


    private int senderTAId;
    private int receiverTAId;


    private String description;
    private int classProctoringId;


}
