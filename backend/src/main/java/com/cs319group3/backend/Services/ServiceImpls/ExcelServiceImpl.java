package com.cs319group3.backend.Services.ServiceImpls;

import com.cs319group3.backend.Entities.*;
import com.cs319group3.backend.Entities.UserEntities.Instructor;
import com.cs319group3.backend.Entities.UserEntities.TA;
import com.cs319group3.backend.Entities.UserEntities.User;
import com.cs319group3.backend.Repositories.*;
import com.cs319group3.backend.Services.ExcelService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ExcelServiceImpl implements ExcelService {

    private static final String TEMPLATE_PATH = "/excelResources/TARequirementsTemplate.xlsx";

    @Autowired
    private CourseTAInstructorFormRepo courseTAInstructorFormRepo;
    @Autowired
    private DepartmentRepo departmentRepo;
    @Autowired
    private CourseRepo courseRepo;
    @Autowired
    private TARepo taRepo;
    @Autowired
    private StudentRepo studentRepo;
    @Autowired
    private TATypeRepo taTypeRepo;
    @Autowired
    private InstructorRepo instructorRepo;


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
        row.createCell(6).setCellValue(form.getPreferredTAs().replace("*", "\n"));
        row.createCell(7).setCellValue(form.getPreferredGraders().replace("*", "\n"));
        row.createCell(8).setCellValue(form.getAvoidedTAs().replace("*", "\n"));
        row.createCell(9).setCellValue(form.getDescription());
    }

    @Override
    public void processTAAssignmentExcel(MultipartFile file) throws IOException {
        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0); // First sheet

            Row headerRow = sheet.getRow(0);
            Row headerRow2 = sheet.getRow(1);

            if (headerRow == null) {
                throw new RuntimeException("empty sheet cannot be processed");
            }

            // Example: Loop through all rows and cells
            for (Row row : sheet) {

                String ss = row.getCell(0).getStringCellValue();

                if (row.getRowNum() < 9)
                    continue; // skip header

                for (Cell cell : row) {

                    if (cell.getColumnIndex() < 4)
                        continue;

                    if (cell.getNumericCellValue() >= 0){

                        int columnIndex = cell.getColumnIndex();
                        String departmentCode = headerRow.getCell(columnIndex).getStringCellValue();
                        double courseCode = headerRow2.getCell(columnIndex).getNumericCellValue();
                        String taId = getStringCellValue(row.getCell(1));

                        assignTA(departmentCode, (int)courseCode, taId);
                        break;
                    }
                } 
            }
        }
    }



    private void assignTA(String departmentCode, int courseCode, String taId) throws RuntimeException{

        Optional<Department> department = departmentRepo.findByDepartmentCode(departmentCode);
        if (!department.isPresent()) {
            throw new RuntimeException("failed because department with code " + departmentCode + " does not exist");
        }

        Optional<Course> course = courseRepo.findByDepartment_DepartmentCodeAndCourseCode(department.get().getDepartmentCode(), courseCode);
        if (!course.isPresent()) {
            throw new RuntimeException("failed because course " + departmentCode + courseCode + " does not exist");
        }

        Optional<TA> ta = taRepo.findByBilkentId(taId);
        if (!ta.isPresent()) {
            throw new RuntimeException("ta with id " + taId + " does not exist");
        }

        TA currTA = ta.get();
        Course currCourse = course.get();

        currTA.setAssignedCourse(currCourse);
        taRepo.save(currTA);
    }

    @Override
    public void uploadStudents(MultipartFile file) throws IOException {
        try(InputStream inputStream = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                // Skip header row
                if (row.getRowNum() < 1)
                    continue;

                // Extract data from Excel cells
                String bilkentId = getStringCellValue(row.getCell(0));
                String name = getStringCellValue(row.getCell(1));
                String surname = getStringCellValue(row.getCell(2));
                String email = getStringCellValue(row.getCell(3));
                String phoneNumber = getStringCellValue(row.getCell(4));
                int classYear = (int) row.getCell(5).getNumericCellValue();
                String departmentCode = getStringCellValue(row.getCell(6));

                // Create the student using extracted data
                uploadStudent(bilkentId, name, surname, email, phoneNumber, classYear, departmentCode);
            }

        }
    }


    // Helper method to safely get string cell values
    private String getStringCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                // Convert numeric to string if needed
                return String.valueOf((int) cell.getNumericCellValue());
            default:
                return "";
        }
    }

    private void uploadStudent(String bilkentId, String name, String surname, String email, String phoneNumber, int classYear, String departmentCode) {
        // Create new student object
        Student newStudent = new Student();
        newStudent.setBilkentId(bilkentId);
        newStudent.setName(name);
        newStudent.setSurname(surname);
        newStudent.setEmail(email);
        newStudent.setPhoneNumber(phoneNumber);
        newStudent.setClassYear(classYear);
        newStudent.setActive(true); // Set as active by default

        // Find and set the department
        Optional<Department> department = departmentRepo.findByDepartmentCode(departmentCode);
        if (department.isPresent()) {
            newStudent.setDepartment(department.get());
        } else {
            // Handle case when department not found
            throw new RuntimeException("Department with code " + departmentCode + " not found");
        }

        studentRepo.save(newStudent);
    }


    @Override
    public void uploadTAs(MultipartFile file) throws IOException{

        try(InputStream inputStream = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() < 1)
                    continue;

                String bilkentId = getStringCellValue(row.getCell(0));
                String name = getStringCellValue(row.getCell(1));
                String surname = getStringCellValue(row.getCell(2));
                String email = getStringCellValue(row.getCell(3));
                String phoneNumber = getStringCellValue(row.getCell(4));
                int classYear = (int) row.getCell(5).getNumericCellValue();
                String departmentCode = getStringCellValue(row.getCell(6));
                String taType = getStringCellValue(row.getCell(7));

                uploadTA(bilkentId, name, surname, email, phoneNumber, classYear, departmentCode, taType);
            }
        }
    }

    private void uploadTA(String bilkentId, String name, String surname, String email, String phoneNumber, int classYear, String departmentCode, String taType){

        TA newTA = new TA();

        Optional<Department> department = departmentRepo.findByDepartmentCode(departmentCode);
        if (department.isPresent()) {
            newTA.setDepartment(department.get());
        } else {
            // Handle case when department not found
            throw new RuntimeException("Department with code " + departmentCode + " not found");
        }

        Optional<TAType> currTAType = taTypeRepo.findByTypeName(taType);
        if (department.isPresent()) {
            newTA.setTaType(currTAType.get());
        } else {
            // Handle case when department not found
            throw new RuntimeException("TA type with " + taType + " not found");
        }

        setUserEssentials(newTA, bilkentId, name, surname, email, phoneNumber);
        newTA.setClassYear(classYear);

        taRepo.save(newTA);
    }


    @Override
    public void uploadInstructors(MultipartFile file) throws IOException {

        try(InputStream inputStream = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() < 1)
                    continue;

                String bilkentId = getStringCellValue(row.getCell(0));
                String name = getStringCellValue(row.getCell(1));
                String surname = getStringCellValue(row.getCell(2));
                String email = getStringCellValue(row.getCell(3));
                String phoneNumber = getStringCellValue(row.getCell(4));
                String departmentCode = getStringCellValue(row.getCell(5));

                uploadInstructor(bilkentId, name, surname, email, phoneNumber, departmentCode);
            }
        }
    }

    private void uploadInstructor(String bilkentId, String name, String surname, String email, String phoneNumber, String departmentCode) {
        Instructor newInstructor = new Instructor();

        Optional<Department> department = departmentRepo.findByDepartmentCode(departmentCode);
        if (department.isPresent()) {
            newInstructor.setDepartment(department.get());
        }
        else {
            throw new RuntimeException("Department with code " + departmentCode + " not found");
        }

        setUserEssentials(newInstructor, bilkentId, name, surname, email, phoneNumber);

        instructorRepo.save(newInstructor);
    }


    private void setUserEssentials(User user, String bilkentId, String name, String surname, String email, String phoneNumber) {
        // Set basic user properties
        user.setBilkentId(bilkentId);
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setActive(true); // Set as active by default
    }
}
