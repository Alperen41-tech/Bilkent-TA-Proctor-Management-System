package com.cs319group3.backend.Services;

import com.cs319group3.backend.DTOs.ProctoringApplicationDTO;

import java.util.List;

public interface ProctoringApplicationService {
    public List<ProctoringApplicationDTO> getProctoringApplications(int deansOfficeId);
}
