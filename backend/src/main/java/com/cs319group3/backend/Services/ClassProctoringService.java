package com.cs319group3.backend.Services;

import com.cs319group3.backend.DTOs.ClassProctoringDTO;
import com.cs319group3.backend.DTOs.CreateClassProctoringDTO;

import java.util.List;

public interface ClassProctoringService {


    public List<ClassProctoringDTO> getClassProctoringList();
    public boolean createClassProctoring(CreateClassProctoringDTO dto);
    public int numberOfRequestsSent(int classProctoringId);
    public int numberOfTAsAssigned(int classProctoringId);
}

