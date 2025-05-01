package com.cs319group3.backend.DTOs;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProctoringApplicationDTO {
    int applicationId;
    ClassProctoringDTO classProctoringDTO;
    int visibleDepartmentId;
    int applicantCountLimit;
    boolean isVisibleForTAs;
    boolean isComplete;
    String finishDate;
}
