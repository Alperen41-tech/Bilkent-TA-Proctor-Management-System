package com.cs319group3.backend.Services;

import com.cs319group3.backend.DTOs.GeneralVariableDTO;
import org.springframework.web.bind.annotation.RequestParam;

public interface GeneralVariableService {
    public boolean changeSemester(String semester);
    public boolean changeProctoringCap(int proctoringCap);
    public GeneralVariableDTO getGeneralVariable();
}
