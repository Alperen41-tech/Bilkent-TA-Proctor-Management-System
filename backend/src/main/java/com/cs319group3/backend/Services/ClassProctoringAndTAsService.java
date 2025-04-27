package com.cs319group3.backend.Services;

import com.cs319group3.backend.DTOs.ClassProctoringAndTAsDTO;

import java.util.List;

public interface ClassProctoringAndTAsService {
    public List<ClassProctoringAndTAsDTO> getDepartmentTAsClassProctorings(int userId);
    public List<ClassProctoringAndTAsDTO> getDepartmentClassProctoringsByCode(String departmentCode);
    public List<ClassProctoringAndTAsDTO> getFacultyClassProctorings(int userId);
    public List<ClassProctoringAndTAsDTO> getDepartmentClassProctoringsById(int departmentId);
    public List<ClassProctoringAndTAsDTO> getClassProctoringsOfCreator(int creatorId);
}
