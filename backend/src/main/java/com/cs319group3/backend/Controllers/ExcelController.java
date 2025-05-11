package com.cs319group3.backend.Controllers;

import com.cs319group3.backend.Services.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Controller responsible for exporting and importing Excel files
 * for TA assignment and data management.
 */
@RestController
@RequestMapping("excel")
@CrossOrigin(origins = "http://localhost:3000")
public class ExcelController {

    @Autowired
    private ExcelService excelService;

    /**
     * Generates and downloads the TA Requirements Excel file.
     *
     * @return a ResponseEntity containing the Excel file bytes and headers
     */
    @GetMapping("/getRequirementsExcel")
    public ResponseEntity<byte[]> getRequirementsExcel() throws IOException {
        byte[] excelData = excelService.generateExcelFromTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDisposition(ContentDisposition
                .attachment()
                .filename("TARequirements.xlsx")
                .build());
        headers.setContentLength(excelData.length);

        return new ResponseEntity<>(excelData, headers, HttpStatus.OK);
    }

    /**
     * Processes the TA assignment Excel file uploaded by the user.
     *
     * @param file the uploaded Excel file
     * @return ResponseEntity indicating success or failure
     */
    @PostMapping("processTAAssignmentExcel")
    public ResponseEntity<Boolean> processTAAssignmentExcel(@RequestParam(name = "file") MultipartFile file) throws IOException {
        excelService.processTAAssignmentExcel(file);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    /**
     * Uploads and processes the full dataset from the Excel file.
     *
     * @param file the uploaded Excel file
     * @return ResponseEntity indicating success or failure
     */
    @PostMapping("uploadAllData")
    public ResponseEntity<Boolean> uploadAllData(@RequestParam(name = "file") MultipartFile file) throws IOException {
        excelService.uploadAllData(file);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping("getStudentsOfClassProctoring")
    public ResponseEntity<byte[]> getStudentsOfClassProctoring(@RequestParam(name = "classProctoringId") int classProctoringId) throws IOException {
        byte[] excelData = excelService.getStudentsOfClassProctoring(classProctoringId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDisposition(ContentDisposition
                .attachment()
                .filename("Students.xlsx")
                .build());
        headers.setContentLength(excelData.length);

        return new ResponseEntity<>(excelData, headers, HttpStatus.OK);
    }


    @GetMapping("getReport")
    public ResponseEntity<byte[]> getReport() throws IOException {
        byte[] excelData = excelService.getReport();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDisposition(ContentDisposition
                .attachment()
                .filename("Report" + LocalDate.now() +".xlsx")
                .build());
        headers.setContentLength(excelData.length);

        return new ResponseEntity<>(excelData, headers, HttpStatus.OK);
    }





}
