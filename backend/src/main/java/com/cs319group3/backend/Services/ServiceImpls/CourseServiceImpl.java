package com.cs319group3.backend.Services.ServiceImpls;

import com.cs319group3.backend.DTOMappers.CourseMapper;
import com.cs319group3.backend.DTOMappers.OfferedCourseMapper;
import com.cs319group3.backend.DTOs.CourseDTO;
import com.cs319group3.backend.DTOs.OfferedCourseDTO;
import com.cs319group3.backend.Entities.Course;
import com.cs319group3.backend.Entities.Department;
import com.cs319group3.backend.Entities.OfferedCourse;
import com.cs319group3.backend.Entities.Semester;
import com.cs319group3.backend.Entities.UserEntities.Instructor;
import com.cs319group3.backend.Repositories.*;
import com.cs319group3.backend.Services.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseRepo courseRepo;
    @Override
    public List<CourseDTO> getCoursesByDepartmentId(int departmentId) {
        List<Course> courses = courseRepo.findByDepartmentDepartmentId(departmentId);
        List<CourseDTO> courseDTOs = new ArrayList<>();
        for (Course course : courses) {
            CourseDTO courseDTO = CourseMapper.toCourseDTO(course);
            courseDTOs.add(courseDTO);
        }
        return courseDTOs;
    }

    @Autowired
    OfferedCourseRepo offeredCourseRepo;

    @Autowired
    GeneralVariableRepo generalVariableRepo;

    @Override
    public List<OfferedCourseDTO> getCoursesOfInstructor(int instructorId){
        List<Integer> offeredCourseIds = offeredCourseRepo.findOfferedCourseIdByInstructorId(instructorId);
        List<OfferedCourseDTO> ocDTOs = new ArrayList<>();
        for(Integer offeredCourseId : offeredCourseIds){
            Optional<OfferedCourse> offeredCourse = offeredCourseRepo.findByOfferedCourseIdAndSemester_SemesterId(offeredCourseId, generalVariableRepo.getSemesterId());
            if(offeredCourse.isPresent()){
                OfferedCourseDTO dto = OfferedCourseMapper.toDTO(offeredCourse.get());
                ocDTOs.add(dto);
            }
        }
        return ocDTOs;
    }

    @Autowired
    DepartmentRepo departmentRepo;

    @Autowired
    InstructorRepo instructorRepo;

    @Override
    public boolean createCourse(CourseDTO dto) {
        Optional<Department> department = departmentRepo.findById(dto.getDepartmentId());
        if(department.isEmpty()){
            return false;
        }
        Optional<Instructor> instructor = instructorRepo.findByUserId(dto.getCoordinatorId());
        if(instructor.isEmpty()){
            return false;
        }
        Course course = new Course();
        course.setDepartment(department.get());
        course.setCoordinator(instructor.get());
        course.setCourseCode(dto.getCourseCode());
        course.setCourseName(dto.getName());
        courseRepo.save(course);
        return true;
    }

    @Autowired
    SemesterRepo semesterRepo;

    @Override
    public boolean createOfferedCourse(OfferedCourseDTO dto) {
        Optional<Course> course = courseRepo.findByCourseId(dto.getCourseId());
        if(course.isEmpty()){
            return false;
        }
        Optional<Semester> semester = semesterRepo.findBySemesterId(dto.getSemesterId());
        if(semester.isEmpty()){
            return false;
        }
        OfferedCourse offeredCourse = new OfferedCourse();
        offeredCourse.setCourse(course.get());
        offeredCourse.setSemester(semester.get());
        offeredCourse.setSectionNo(dto.getSectionNo());
        offeredCourseRepo.save(offeredCourse);
        return true;
    }
}
