package com.cs319group3.backend.Services.ServiceImpls;

import com.cs319group3.backend.CompositeIDs.ClassProctoringClassroomKey;
import com.cs319group3.backend.CompositeIDs.CourseInstructorKey;
import com.cs319group3.backend.CompositeIDs.CourseStudentKey;
import com.cs319group3.backend.CompositeIDs.OfferedCourseScheduleKey;
import com.cs319group3.backend.Entities.*;
import com.cs319group3.backend.Entities.RelationEntities.CourseInstructorRelation;
import com.cs319group3.backend.Entities.RelationEntities.CourseStudentRelation;
import com.cs319group3.backend.Entities.RelationEntities.OfferedCourseScheduleRelation;
import com.cs319group3.backend.Entities.UserEntities.*;
import com.cs319group3.backend.Repositories.*;
import com.cs319group3.backend.Services.ExcelService;
import org.apache.poi.sl.draw.geom.GuideIf;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExcelServiceImpl implements ExcelService {

    private static final String TEMPLATE_PATH = "/excelResources/";

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

    @Autowired
    private SemesterRepo semesterRepo;

    @Autowired
    private OfferedCourseRepo offeredCourseRepo;

    @Autowired
    private TimeIntervalRepo timeIntervalRepo;

    @Autowired
    private OfferedCourseScheduleRelationRepo offeredCourseScheduleRelationRepo;

    @Autowired
    private LoginRepo loginRepo;

    @Autowired
    private UserTypeRepo userTypeRepo;

    @Autowired
    private CourseStudentRelationRepo courseStudentRelationRepo;

    @Autowired
    private ClassProctoringRepo classProctoringRepo;
    @Autowired
    private CourseInstructorRelationRepo courseInstructorRelationRepo;
    @Autowired
    private FacultyRepo facultyRepo;
    @Autowired
    private DeansOfficeRepo deansOfficeRepo;
    @Autowired
    private DepartmentSecretaryRepo departmentSecretaryRepo;
    @Autowired
    private ClassProctoringTARelationRepo classProctoringTARelationRepo;

    public byte[] generateExcelFromTemplate() throws IOException {
        // Load template from classpath (inside the .jar)
        List<CourseTAInstructorForm> forms = courseTAInstructorFormRepo.findAll();

        try (InputStream templateStream = getClass().getResourceAsStream(TEMPLATE_PATH + "TARequirementsTemplate.xlsx");
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
        row.createCell(1).setCellValue(form.getCourse().getCourseFullCode());
        row.createCell(2).setCellValue(form.getMinTALoad());
        row.createCell(3).setCellValue(form.getMaxTALoad());
        row.createCell(4).setCellValue(form.getNumberOfGrader());
        row.createCell(5).setCellValue(form.getMustHaveTAs().replace("*", "\n"));
        row.createCell(6).setCellValue(form.getPreferredTAs().replace("*", "\n"));
        row.createCell(7).setCellValue(form.getPreferredGraders().replace("*", "\n"));
        row.createCell(8).setCellValue(form.getAvoidedTAs().replace("*", "\n"));
        row.createCell(9).setCellValue(form.getDescription());
    }

    private void setRow(Row row, Student student, String classroom){
        row.createCell(0).setCellValue(student.getBilkentId());
        row.createCell(1).setCellValue(student.getName());
        row.createCell(2).setCellValue(student.getSurname());
        row.createCell(3).setCellValue(student.getEmail());
        row.createCell(4).setCellValue(classroom);
    }

    private void setRow(Row row, TA ta, int paidProctoring, int unpaidProctoring){
        row.createCell(0).setCellValue(ta.getBilkentId());
        row.createCell(1).setCellValue(ta.getDepartment().getDepartmentCode());
        row.createCell(2).setCellValue(ta.getName());
        row.createCell(3).setCellValue(ta.getSurname());
        row.createCell(4).setCellValue(ta.getEmail());
        row.createCell(5).setCellValue(ta.getPhoneNumber());
        row.createCell(6).setCellValue(ta.getClassYear());
        row.createCell(7).setCellValue(ta.getTaType().getTypeName());
        row.createCell(8).setCellValue(ta.getWorkload());
        row.createCell(9).setCellValue(paidProctoring);
        row.createCell(10).setCellValue(unpaidProctoring);
    }

    @Override
    public byte[] getStudentsOfClassProctoring(int classProctoringId) throws IOException {

        Optional<ClassProctoring> classProctoringOptional = classProctoringRepo.findById(classProctoringId);
        if (!classProctoringOptional.isPresent()) {
            throw new RuntimeException("ClassProctoring with id" + classProctoringId +" not found");
        }

        List<ClassProctoringClassroom> classrooms = classProctoringOptional.get().getClassrooms();

        List<String> classroomNames = new ArrayList<>();

        for (ClassProctoringClassroom classroom : classrooms) {
            ClassProctoringClassroomKey key = classroom.getId();
            classroomNames.add(key.getClassroom());
        }


        Course offeredCourse = classProctoringOptional.get().getCourse();

        int sectionNo = classProctoringOptional.get().getSectionNo();

        List<CourseStudentRelation> relations = new ArrayList<>();

        if (sectionNo == 0){
            relations = courseStudentRelationRepo.findByCourse_Course(offeredCourse);
        }
        else {
            relations = courseStudentRelationRepo.findByCourse_CourseAndCourse_SectionNo(offeredCourse, sectionNo);
        }

        int classroomCount = classrooms.size();
        int studentCount = relations.size();
        int classroomSize = (int) Math.ceil(studentCount / classroomCount);


        try (InputStream templateStream = getClass().getResourceAsStream(TEMPLATE_PATH + "Students.xlsx");
             Workbook wb = new XSSFWorkbook(templateStream);
             ByteArrayOutputStream outStream = new ByteArrayOutputStream()) {

            Sheet sheet = wb.getSheetAt(0);
            int rowIndex = sheet.getLastRowNum() + 1;
            int classRoomPtr = 0;

            for (int i = 1; i <= relations.size(); i++) {
                Row row = sheet.createRow(rowIndex++);

                if (i % classroomSize == 0){
                    classRoomPtr++;
                }
                String classroom = classroomNames.get(Math.min(classRoomPtr, classroomCount - 1));

                setRow(row, relations.get(i - 1).getStudent(), classroom);
            }

            wb.write(outStream);
            return outStream.toByteArray();
        }
    }

    @Override
    public byte[] getReport() throws IOException{

        try (InputStream templateStream = getClass().getResourceAsStream(TEMPLATE_PATH + "Report.xlsx");
            Workbook wb = new XSSFWorkbook(templateStream);
            ByteArrayOutputStream outStream = new ByteArrayOutputStream()){

            Sheet sheet = wb.getSheetAt(0);
            int rowIndex = sheet.getLastRowNum() + 1;

            List<TA> allTA = taRepo.findAll();

            for (TA ta : allTA) {
                Row row = sheet.createRow(rowIndex++);

                int paidProctoring = classProctoringTARelationRepo.countByTA_UserIdAndIsPaid(ta.getUserId(), true);
                int unpaidProctoring = classProctoringTARelationRepo.countByTA_UserIdAndIsPaid(ta.getUserId(), false);

                setRow(row, ta, paidProctoring, unpaidProctoring);

            }

            wb.write(outStream);
            return outStream.toByteArray();
        }

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



                if (row.getRowNum() < 9)
                    continue; // skip header

                String ss = getStringCellValue(row.getCell(0));

                if (ss.isEmpty()){
                    break;
                }
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


    @Override
    public void uploadAllData(MultipartFile file) throws IOException {

        try(InputStream inputStream = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet facultySheet = workbook.getSheetAt(0);
            Sheet departmentSheet = workbook.getSheetAt(1);
            Sheet studentsSheet = workbook.getSheetAt(2);
            Sheet tasSheet = workbook.getSheetAt(3);
            Sheet instructorSheet = workbook.getSheetAt(4);
            Sheet courseSheet = workbook.getSheetAt(5);
            Sheet offeredCoursesSheet = workbook.getSheetAt(6);
            Sheet scheduleSheet = workbook.getSheetAt(7);
            Sheet offeredCourseStudentRelSheet = workbook.getSheetAt(8);
            Sheet offeredCourseInstructorRelSheet = workbook.getSheetAt(9);


            //uploadFaculty(facultySheet);
            //uploadDepartment(departmentSheet);
            uploadStudents(studentsSheet);
            uploadTAs(tasSheet);
            uploadInstructors(instructorSheet);
            uploadCourses(courseSheet);
            uploadOfferedCourses(offeredCoursesSheet);
            uploadSchedule(scheduleSheet);
            uploadOfferedCourseStudentRel(offeredCourseStudentRelSheet);
            uploadOfferedCourseInstructorRel(offeredCourseInstructorRelSheet);
        }

    }

    private void uploadDepartment(Sheet departmentSheet) {
        for (Row row : departmentSheet) {
            if (row.getRowNum() < 1)
                continue;

            String departmentName = getStringCellValue(row.getCell(0));
            String departmentCode = getStringCellValue(row.getCell(1));

            Optional<Faculty> faculty = facultyRepo.findByFacultyName(getStringCellValue(row.getCell(2)));
            if (!faculty.isPresent()) {
                throw new RuntimeException("faculty with name " + getStringCellValue(row.getCell(2)) + " does not exist");
            }

            Department department = new Department();
            department.setDepartmentName(departmentName);
            department.setDepartmentCode(departmentCode);
            department.setFaculty(faculty.get());
            department = departmentRepo.save(department);

            DepartmentSecretary departmentSecretary = new DepartmentSecretary();
            departmentSecretary.setDepartment(department);
            departmentSecretary.setBilkentId(getStringCellValue(row.getCell(3)));
            departmentSecretary.setName(getStringCellValue(row.getCell(4)));
            departmentSecretary.setSurname(getStringCellValue(row.getCell(5)));
            departmentSecretary.setEmail(getStringCellValue(row.getCell(6)));
            departmentSecretary.setPhoneNumber(getStringCellValue(row.getCell(7)));
            departmentSecretary.setActive(true);

            departmentSecretaryRepo.save(departmentSecretary);

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String password = getStringCellValue(row.getCell(8));

            Login login = new Login();
            login.setUser(departmentSecretary);
            login.setPassword(encoder.encode(password));
            UserType type = userTypeRepo.findByUserTypeName("department secretary");
            login.setUserType(type);
            loginRepo.save(login);
        }
    }

    private void uploadFaculty(Sheet facultySheet) {
        for (Row row : facultySheet) {
            if (row.getRowNum() < 1)
                continue;

            String facultyName = getStringCellValue(row.getCell(0));
            Faculty faculty = new Faculty();
            faculty.setFacultyName(facultyName);
            facultyRepo.save(faculty);

            DeansOffice deansOffice = new DeansOffice();
            deansOffice.setFaculty(faculty);
            deansOffice.setBilkentId(getStringCellValue(row.getCell(1)));
            deansOffice.setName(getStringCellValue(row.getCell(2)));
            deansOffice.setSurname(getStringCellValue(row.getCell(3)));
            deansOffice.setEmail(getStringCellValue(row.getCell(4)));
            deansOffice.setPhoneNumber(getStringCellValue(row.getCell(5)));
            deansOffice.setActive(true);
            deansOfficeRepo.save(deansOffice);

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String password = getStringCellValue(row.getCell(6));

            Login login = new Login();
            login.setUser(deansOffice);
            login.setPassword(encoder.encode(password));
            UserType type = userTypeRepo.findByUserTypeName("deans office");
            login.setUserType(type);

            loginRepo.save(login);
        }
    }



    private void uploadOfferedCourseInstructorRel(Sheet offeredCourseInstructorRelSheet) {

        for (Row row : offeredCourseInstructorRelSheet) {
            if (row.getRowNum() < 1)
                continue;

            String departmentCode = getStringCellValue(row.getCell(0));
            int courseCode = (int) row.getCell(1).getNumericCellValue();
            int sectionNo = (int) row.getCell(2).getNumericCellValue();
            String semester = getStringCellValue(row.getCell(3));
            int term = (int) row.getCell(4).getNumericCellValue();
            String instructorId = getStringCellValue(row.getCell(5));

            saveOfferedCourseInstructorRel(departmentCode, courseCode, sectionNo, semester, term, instructorId);
        }
    }

    private void saveOfferedCourseInstructorRel(String departmentCode, int courseCode, int sectionNo, String semester, int term, String instructorId) {
        Optional<Course> course = courseRepo.findByDepartment_DepartmentCodeAndCourseCode(departmentCode, courseCode);
        if (!course.isPresent()) {
            throw new RuntimeException("failed because course with code " + departmentCode + " " + courseCode + " does not exist");
        }

        Optional<Semester> currSemester = semesterRepo.findByYearAndTerm(semester, term);
        if (!currSemester.isPresent()) {
            throw new RuntimeException("failed because semester " + semester + " does not exist");
        }


        Optional<OfferedCourse> offeredCourse = offeredCourseRepo.findByCourseAndSemesterAndSectionNo(course.get(), currSemester.get(), sectionNo);
        if (!offeredCourse.isPresent()) {
            throw new RuntimeException("failed because course " + courseCode + " does not exist");
        }

        Optional<Instructor> instructorOptional = instructorRepo.findByBilkentId(instructorId);

        if (!instructorOptional.isPresent()) {
            throw new RuntimeException("failed because student with id " + instructorId + " does not exist");
        }

        CourseInstructorKey key = new CourseInstructorKey(offeredCourse.get().getOfferedCourseId(), instructorOptional.get().getUserId());

        CourseInstructorRelation relation = new CourseInstructorRelation();
        relation.setId(key);
        relation.setCourse(offeredCourse.get());
        relation.setInstructor(instructorOptional.get());

        courseInstructorRelationRepo.save(relation);
    }


    private void uploadOfferedCourseStudentRel(Sheet offeredCourseStudentRelSheet) {
        for (Row row : offeredCourseStudentRelSheet) {
            if (row.getRowNum() < 1)
                continue;

            String departmentCode = getStringCellValue(row.getCell(0));
            int courseCode = (int) row.getCell(1).getNumericCellValue();
            int sectionNo = (int) row.getCell(2).getNumericCellValue();
            String semester = getStringCellValue(row.getCell(3));
            int term = (int) row.getCell(4).getNumericCellValue();
            String studentId = getStringCellValue(row.getCell(5));

            saveOfferedCourseStudentRel(departmentCode, courseCode, sectionNo, semester, term, studentId);
        }
    }

    private void saveOfferedCourseStudentRel(String departmentCode, int courseCode, int sectionNo, String semester, int term, String studentId) {

        Optional<Course> course = courseRepo.findByDepartment_DepartmentCodeAndCourseCode(departmentCode, courseCode);
        if (!course.isPresent()) {
            throw new RuntimeException("failed because course with code " + courseCode + " does not exist");
        }

        Optional<Semester> currSemester = semesterRepo.findByYearAndTerm(semester, term);
        if (!currSemester.isPresent()) {
            throw new RuntimeException("failed because semester " + semester + " does not exist");
        }


        Optional<OfferedCourse> offeredCourse = offeredCourseRepo.findByCourseAndSemesterAndSectionNo(course.get(), currSemester.get(), sectionNo);
        if (!offeredCourse.isPresent()) {
            throw new RuntimeException("failed because course " + courseCode + " does not exist");
        }

        Optional<Student> studentOptional = studentRepo.findByBilkentId(studentId);
        if (!studentOptional.isPresent()) {
            throw new RuntimeException("failed because student with id " + studentId + " does not exist");
        }

        CourseStudentKey key = new CourseStudentKey(offeredCourse.get().getOfferedCourseId(), studentOptional.get().getStudentId());

        CourseStudentRelation courseStudentRelation = new CourseStudentRelation();
        courseStudentRelation.setId(key);
        courseStudentRelation.setCourse(offeredCourse.get());
        courseStudentRelation.setStudent(studentOptional.get());

        courseStudentRelationRepo.save(courseStudentRelation);
    }

    private void uploadSchedule(Sheet scheduleSheet) {

        for (Row row : scheduleSheet) {
            // Skip header row
            if (row.getRowNum() < 1)
                continue;

            String departmentCode = getStringCellValue(row.getCell(0));
            int courseCode = (int) row.getCell(1).getNumericCellValue();
            int sectionNo = (int) row.getCell(2).getNumericCellValue();
            String semester = getStringCellValue(row.getCell(3));
            int term = (int) row.getCell(4).getNumericCellValue();
            String day = getStringCellValue(row.getCell(5));
            int block = (int) row.getCell(6).getNumericCellValue();

            saveSchedule(departmentCode, courseCode, sectionNo, semester, term, day, block);

        }
    }

    private void saveSchedule(String departmentCode, int courseCode, int sectionNo, String semester, int term ,String day, int block) {

        Optional<Course> course = courseRepo.findByDepartment_DepartmentCodeAndCourseCode(departmentCode, courseCode);
        if (!course.isPresent()) {
            throw new RuntimeException("failed because course with code " + courseCode + " does not exist");
        }

        Optional<Semester> currSemester = semesterRepo.findByYearAndTerm(semester, term);
        if (!currSemester.isPresent()) {
            throw new RuntimeException("failed because semester " + semester + " does not exist");
        }


        Optional<OfferedCourse> offeredCourse = offeredCourseRepo.findByCourseAndSemesterAndSectionNo(course.get(), currSemester.get(), sectionNo);
        if (!offeredCourse.isPresent()) {
            throw new RuntimeException("failed because course " + courseCode + " does not exist");
        }

        int dayth;
        if (day.equals("Monday")){
            dayth = 0;
        }else if (day.equals("Tuesday")){
            dayth = 1;
        }else if (day.equals("Wednesday")){
            dayth = 2;
        }else if (day.equals("Thursday")){
            dayth = 3;
        }else if (day.equals("Friday")){
            dayth = 4;
        }else {
            throw new RuntimeException("failed because day " + day + " does not exist");
        }

        int timeIntervalId = dayth * 9 + block;

        TimeInterval timeInterval = timeIntervalRepo.findById(timeIntervalId).get();

        OfferedCourseScheduleKey key = new OfferedCourseScheduleKey(
                offeredCourse.get().getOfferedCourseId(),  // Make sure this getter exists
                timeInterval.getTimeIntervalId()           // Make sure this getter exists
        );

        // Check if this relationship already exists
        if (offeredCourseScheduleRelationRepo.existsById(key)) {
            // if schedule exist just pass over;
            return;
        }

        // Create and save the relationship with the proper embedded ID
        OfferedCourseScheduleRelation offeredCourseScheduleRelation = new OfferedCourseScheduleRelation();
        offeredCourseScheduleRelation.setId(key);          // Set the embedded ID
        offeredCourseScheduleRelation.setCourse(offeredCourse.get());
        offeredCourseScheduleRelation.setTimeInterval(timeInterval);

        offeredCourseScheduleRelationRepo.save(offeredCourseScheduleRelation);
    }


    private void uploadStudents(Sheet students)  {
        for (Row row : students) {
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
            saveStudent(bilkentId, name, surname, email, phoneNumber, classYear, departmentCode);
        }
    }



    private void saveStudent(String bilkentId, String name, String surname, String email, String phoneNumber, int classYear, String departmentCode) {
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

    private void uploadTAs(Sheet taSheet){

        for (Row row : taSheet) {
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
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String password = passwordEncoder.encode(getStringCellValue(row.getCell(8)));

            saveTA(bilkentId, name, surname, email, phoneNumber, classYear, departmentCode, taType, password);
        }
    }

    private void saveTA(String bilkentId, String name, String surname, String email, String phoneNumber, int classYear, String departmentCode, String taType, String password){

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

        UserType userType = userTypeRepo.findByUserTypeName("ta");

        taRepo.save(newTA);
        saveLogin(newTA, password, userType);
    }

    private void saveLogin(User user, String password, UserType type){
        Login newLogin = new Login();
        newLogin.setUser(user);
        newLogin.setPassword(password);
        newLogin.setUserType(type);
        loginRepo.save(newLogin);
    }


    private void uploadInstructors(Sheet instructorSheet) throws IOException {

        for (Row row : instructorSheet) {
            if (row.getRowNum() < 1)
                continue;

            String bilkentId = getStringCellValue(row.getCell(0));
            String name = getStringCellValue(row.getCell(1));
            String surname = getStringCellValue(row.getCell(2));
            String email = getStringCellValue(row.getCell(3));
            String phoneNumber = getStringCellValue(row.getCell(4));
            String departmentCode = getStringCellValue(row.getCell(5));
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String password = passwordEncoder.encode(getStringCellValue(row.getCell(6)));

            saveInstructor(bilkentId, name, surname, email, phoneNumber, departmentCode, password);
        }
    }



    private void saveInstructor(String bilkentId, String name, String surname, String email, String phoneNumber, String departmentCode, String password) {
        Instructor newInstructor = new Instructor();

        Optional<Department> department = departmentRepo.findByDepartmentCode(departmentCode);
        if (department.isPresent()) {
            newInstructor.setDepartment(department.get());
        }
        else {
            throw new RuntimeException("Department with code " + departmentCode + " not found");
        }

        setUserEssentials(newInstructor, bilkentId, name, surname, email, phoneNumber);

        UserType userType = userTypeRepo.findByUserTypeName("instructor");

        instructorRepo.save(newInstructor);
        saveLogin(newInstructor, password, userType);
    }

    private void uploadCourses(Sheet courseSheet){

        for (Row row : courseSheet) {
            if (row.getRowNum() < 1)
                continue;

            String departmentCode = getStringCellValue(row.getCell(0));
            double courseCode = row.getCell(1).getNumericCellValue();
            String courseName = getStringCellValue(row.getCell(2));
            String coordinatorId = getStringCellValue(row.getCell(3));

            saveCourse(departmentCode, (int) courseCode, courseName, coordinatorId);
        }
    }

    private void saveCourse(String departmentCode, int courseCode, String courseName, String coordinatorId) {

        Course course = new Course();
        Optional<Department> department = departmentRepo.findByDepartmentCode(departmentCode);
        if (!department.isPresent()) {
            throw new RuntimeException("Department with code " + departmentCode + " not found");
        }

        Optional<Instructor> instructor = instructorRepo.findByBilkentId(coordinatorId);
        if (!instructor.isPresent()) {
            throw new RuntimeException("Instructor with id " + coordinatorId + " not found");
        }

        course.setCoordinator(instructor.get());
        course.setDepartment(department.get());
        course.setCourseCode(courseCode);
        course.setCourseName(courseName);
        courseRepo.save(course);
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


    private void uploadOfferedCourses(Sheet offeredCoursesSheet) {
        for (Row row : offeredCoursesSheet) {
            // Skip header row
            if (row.getRowNum() < 1)
                continue;

            String departmentCode = getStringCellValue(row.getCell(0));
            int courseCode = (int) row.getCell(1).getNumericCellValue();
            int sectionNo = (int) row.getCell(2).getNumericCellValue();
            String semester = getStringCellValue(row.getCell(3));
            int term = (int) row.getCell(4).getNumericCellValue();

            saveOfferedCourse(departmentCode, courseCode, sectionNo, semester, term);

        }
    }

    private void saveOfferedCourse(String departmentCode, int courseCode, int sectionNo, String semester, int term) {

        Optional<Course> course = courseRepo.findByDepartment_DepartmentCodeAndCourseCode(departmentCode, courseCode);
        if (!course.isPresent()) {
            throw new RuntimeException("failed because course with code " + departmentCode + " " + courseCode + " does not exist");
        }

        Optional<Semester> currSemester = semesterRepo.findByYearAndTerm(semester, term);
        if (!currSemester.isPresent()) {
            throw new RuntimeException("failed because semester " + semester + " does not exist");
        }

        OfferedCourse offeredCourse = new OfferedCourse();
        offeredCourse.setCourse(course.get());
        offeredCourse.setSectionNo(sectionNo);
        offeredCourse.setSemester(currSemester.get());

        offeredCourseRepo.save(offeredCourse);
    }




}
