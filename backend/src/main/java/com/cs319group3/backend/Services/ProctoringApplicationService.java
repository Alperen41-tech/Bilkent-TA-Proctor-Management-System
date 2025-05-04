package com.cs319group3.backend.Services;

import com.cs319group3.backend.DTOs.ProctoringApplicationDTO;
import com.cs319group3.backend.Enums.ProctoringApplicationType;

import java.util.List;

public interface ProctoringApplicationService {
    public List<ProctoringApplicationDTO> getProctoringApplications(int deansOfficeId);
    public boolean createProctoringApplication(int classProctoringId, ProctoringApplicationDTO dto, int deansOfficeId);
    public boolean createProctoringApplications(int classProctoringId, List<ProctoringApplicationDTO> dto, int deansOfficeId);

    public List<ProctoringApplicationDTO> getAllApplicationsByDepartment(int departmentId);

    public boolean setApplicationType(int applicationId, ProctoringApplicationType type);
}
