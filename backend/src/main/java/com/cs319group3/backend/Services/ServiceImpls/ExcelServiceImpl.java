package com.cs319group3.backend.Services.ServiceImpls;

import com.cs319group3.backend.Entities.CourseTAInstructorForm;
import com.cs319group3.backend.Repositories.CourseTAInstructorFormRepo;
import com.cs319group3.backend.Services.ExcelService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

@Service
public class ExcelServiceImpl implements ExcelService {


    @Autowired
    private CourseTAInstructorFormRepo courseTAInstructorFormRepo;

    private static final String TEMPLATE_PATH = "/excelResources/TARequirementsTemplate.xlsx";

    public byte[] generateExcelFromTemplate() throws IOException {
        // Load template from classpath (inside the .jar)
        List<CourseTAInstructorForm> forms = courseTAInstructorFormRepo.findAll();

        try (InputStream templateStream = getClass().getResourceAsStream(TEMPLATE_PATH);
             Workbook wb = new XSSFWorkbook(templateStream);
             ByteArrayOutputStream outStream = new ByteArrayOutputStream()) {

            Sheet sheet = wb.getSheetAt(0);
            int rowIndex = sheet.getLastRowNum() + 1;

            for (CourseTAInstructorForm form : forms) {
                Row row = sheet.createRow(rowIndex++);
                setRow(row, form);
            }

            wb.write(outStream);
            return outStream.toByteArray();
        }
    }

    private void setRow(Row row, CourseTAInstructorForm form){
        row.createCell(0).setCellValue(form.getInstructor().getFullName());
        row.createCell(1).setCellValue(form.getCourse().getCourseFullName());
        row.createCell(2).setCellValue(form.getMinTALoad());
        row.createCell(3).setCellValue(form.getMaxTALoad());
        row.createCell(4).setCellValue(form.getNumberOfGrader());
        row.createCell(5).setCellValue(form.getMustHaveTAs().replace("*", "\n"));
        row.createCell(6).setCellValue(form.getPreferredTAs().replace("*", ""));
        row.createCell(7).setCellValue(form.getPreferredGraders().replace("*", ""));
        row.createCell(8).setCellValue(form.getAvoidedTAs().replace("*", ""));
        row.createCell(9).setCellValue(form.getDescription());
    }


}
