package com.cs319group3.backend.Services.ServiceImpls;

import com.cs319group3.backend.DTOMappers.CourseMapper;
import com.cs319group3.backend.DTOs.CourseDTO;
import com.cs319group3.backend.Entities.Course;
import com.cs319group3.backend.Repositories.CourseRepo;
import com.cs319group3.backend.Services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
}
