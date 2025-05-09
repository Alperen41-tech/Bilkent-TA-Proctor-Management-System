package com.cs319group3.backend.Controllers;

import com.cs319group3.backend.Services.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<byte[]> getRequirementsExcel() {
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Processes the TA assignment Excel file uploaded by the user.
     *
     * @param file the uploaded Excel file
     * @return ResponseEntity indicating success or failure
     */
    @PostMapping("processTAAssignmentExcel")
    public ResponseEntity<Boolean> processTAAssignmentExcel(@RequestParam(name = "file") MultipartFile file) {
        try {
            excelService.processTAAssignmentExcel(file);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Uploads and processes the full dataset from the Excel file.
     *
     * @param file the uploaded Excel file
     * @return ResponseEntity indicating success or failure
     */
    @PostMapping("uploadAllData")
    public ResponseEntity<Boolean> uploadAllData(@RequestParam(name = "file") MultipartFile file) {
        try {
            excelService.uploadAllData(file);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("getStudentsOfClassProctoring")
    public ResponseEntity<byte[]> getStudentsOfClassProctoring(@RequestParam(name = "classProctoringId") int classProctoringId) {
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





}
