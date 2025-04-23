package com.cs319group3.backend.Services;

import com.cs319group3.backend.DTOs.ClassProctoringAndTAsDTO;

import java.util.List;

public interface ClassProctoringAndTAsService {
    public List<ClassProctoringAndTAsDTO> getDepartmentTAsClassProctorings(int userId);
}
