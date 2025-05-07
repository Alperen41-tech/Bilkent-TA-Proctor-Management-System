package com.cs319group3.backend.Controllers;

import com.cs319group3.backend.Services.ExcelService;
import org.apache.coyote.Response;
import org.etsi.uri.x01903.v13.ResponderIDType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("excel")
@CrossOrigin(origins = "http://localhost:3000")
public class ExcelController {

    @Autowired
    private ExcelService excelService;

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


    @PostMapping("processTAAssignmentExcel")
    public ResponseEntity<Boolean> processTAAssignmentExcel(@RequestParam(name = "file") MultipartFile file) {
        try{

            excelService.processTAAssignmentExcel(file);

            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(false,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("uploadAllData")
    public ResponseEntity<Boolean> uploadAllData(@RequestParam(name = "file") MultipartFile file) {
        try {
            excelService.uploadAllData(file);
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(false,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





}
