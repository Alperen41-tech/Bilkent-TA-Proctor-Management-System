package com.cs319group3.backend.DTOMappers;

import com.cs319group3.backend.DTOs.OfferedCourseDTO;
import com.cs319group3.backend.Entities.OfferedCourse;

public class OfferedCourseMapper {
    public static OfferedCourseDTO toDTO(OfferedCourse offeredCourse) {
        OfferedCourseDTO dto = new OfferedCourseDTO();
        dto.setCourse(CourseMapper.toCourseDTO(offeredCourse.getCourse()));
        dto.setOfferedCourseId(offeredCourse.getOfferedCourseId());
        dto.setSemesterId(offeredCourse.getSemester().getSemesterId());
        dto.setSectionNo(offeredCourse.getSectionNo());
        return dto;
    }
}
