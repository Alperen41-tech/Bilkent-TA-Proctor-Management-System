package com.cs319group3.backend.DTOs;

import com.cs319group3.backend.Enums.ProctoringApplicationType;
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
    ProctoringApplicationType applicationType;
    boolean isComplete;
    String finishDate;
    int applicantCount;
}
