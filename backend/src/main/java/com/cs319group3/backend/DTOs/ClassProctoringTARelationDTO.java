package com.cs319group3.backend.DTOs;


import lombok.*;

@Data
@Getter
@Setter
public class ClassProctoringTARelationDTO {

    private ClassProctoringDTO classProctoringDTO;
    private int TAId;

    private boolean isPaid;
    private boolean isComplete;
    private Boolean isOpenToSwap;
    private boolean swapRequestable;

}
