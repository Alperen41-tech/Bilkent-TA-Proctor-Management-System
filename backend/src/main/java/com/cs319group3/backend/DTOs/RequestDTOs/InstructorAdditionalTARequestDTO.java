package com.cs319group3.backend.DTOs.RequestDTOs;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InstructorAdditionalTARequestDTO {
    RequestDTO requestDTO;
    int taCount;
    boolean isComplete;
    int classProctoringId;
}
