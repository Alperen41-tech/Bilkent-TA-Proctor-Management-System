package com.cs319group3.backend.Services.ServiceImpls;

import com.cs319group3.backend.DTOMappers.CourseMapper;
import com.cs319group3.backend.DTOMappers.OfferedCourseMapper;
import com.cs319group3.backend.DTOs.CourseDTO;
import com.cs319group3.backend.DTOs.OfferedCourseDTO;
import com.cs319group3.backend.Entities.Course;
import com.cs319group3.backend.Entities.OfferedCourse;
import com.cs319group3.backend.Repositories.CourseRepo;
import com.cs319group3.backend.Repositories.GeneralVariableRepo;
import com.cs319group3.backend.Repositories.OfferedCourseRepo;
import com.cs319group3.backend.Services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
}
