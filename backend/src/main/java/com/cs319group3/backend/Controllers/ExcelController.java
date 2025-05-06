package com.cs319group3.backend.Controllers;

import com.cs319group3.backend.Services.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("excel")
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



}
