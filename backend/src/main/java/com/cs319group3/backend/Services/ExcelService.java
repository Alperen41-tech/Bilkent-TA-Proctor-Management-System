package com.cs319group3.backend.Services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ExcelService {

    public byte[] generateExcelFromTemplate() throws IOException;

    void processTAAssignmentExcel(MultipartFile file) throws IOException;

    void uploadStudents(MultipartFile file) throws IOException;

    void uploadTAs(MultipartFile file) throws IOException;

    void uploadInstructors(MultipartFile file) throws IOException;
}
