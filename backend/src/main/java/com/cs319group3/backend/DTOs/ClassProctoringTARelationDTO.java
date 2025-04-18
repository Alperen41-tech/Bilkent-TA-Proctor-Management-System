package com.cs319group3.backend.DTOs;


import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClassProctoringTARelationDTO {



    ClassProctoringDTO classProctoringDTO;
    int TAId;

    boolean isPaid;
    boolean isComplete;
    Boolean isOpenToSwap;

    public boolean isOpenToSwap() {
        return isOpenToSwap;
    }
    public void setOpenToSwap(boolean isOpenToSwap) {
        this.isOpenToSwap = isOpenToSwap;
    }




}
