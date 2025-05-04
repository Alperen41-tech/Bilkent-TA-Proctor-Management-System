package com.cs319group3.backend.DTOs;

import com.cs319group3.backend.Enums.ProctoringApplicationType;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProctoringApplicationDTO {
    private int applicationId;
    private ClassProctoringDTO classProctoringDTO;
    private int visibleDepartmentId;
    private int applicantCountLimit;
    private ProctoringApplicationType applicationType;
    private boolean isComplete;
    private String finishDate;
    private int applicantCount;
    private Boolean isAppliedByTA;
}
