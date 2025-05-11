package com.cs319group3.backend.Services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ExcelService {

    /**
     * Generates an Excel file using a predefined template.
     *
     * @return byte array representing the Excel file
     * @throws IOException if file creation fails
     */
    byte[] generateExcelFromTemplate() throws IOException;

    /**
     * Processes an uploaded Excel file containing TA assignments and
     * updates the corresponding records in the system.
     *
     * @param file the uploaded Excel file
     * @throws IOException if file processing fails
     */
    void processTAAssignmentExcel(MultipartFile file) throws IOException;

    /**
     * Uploads and parses all data from the given Excel file,
     * including courses, users, departments, etc.
     *
     * @param file the uploaded Excel file
     * @throws IOException if file processing fails
     */
    void uploadAllData(MultipartFile file) throws IOException;

    /**
     * Generates an Excel file listing students assigned to a class proctoring.
     *
     * @param classProctoringId the ID of the class proctoring
     * @return byte array representing the Excel file
     * @throws IOException if file generation fails
     */
    byte[] getStudentsOfClassProctoring(int classProctoringId) throws IOException;

    /**
     * used to get annual report showing ta's total workload, paid proctorings and unpaid proctroings
     * @return
     */
    byte[] getReport() throws IOException;
}