package com.cs319group3.backend.Services.ServiceImpls;

import com.cs319group3.backend.DTOMappers.TAScheduleMapper;
import com.cs319group3.backend.DTOs.DateIntervalDTO;
import com.cs319group3.backend.DTOs.TAProfileDTO;
import com.cs319group3.backend.DTOMappers.TAProfileMapper;
import com.cs319group3.backend.DTOs.TAScheduleDTO;
import com.cs319group3.backend.Entities.RelationEntities.ClassProctoringTARelation;
import com.cs319group3.backend.Entities.RelationEntities.CourseStudentRelation;
import com.cs319group3.backend.Entities.RelationEntities.OfferedCourseScheduleRelation;
import com.cs319group3.backend.Entities.Student;
import com.cs319group3.backend.Entities.TimeInterval;
import com.cs319group3.backend.Entities.UserEntities.TA;
import com.cs319group3.backend.Repositories.StudentRepo;
import com.cs319group3.backend.Repositories.TARepo;
import com.cs319group3.backend.Services.TAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class TAServiceImpl implements TAService {

    @Autowired
    private TARepo taRepository;
    @Autowired
    private StudentRepo studentRepo;

    @Override
    public TAProfileDTO getTAProfileById(int id) {
        Optional<TA> optionalTA = taRepository.findByUserId(id);

        if (optionalTA.isEmpty()) {
            throw new RuntimeException("TA with ID " + id + " not found.");
        }

        return TAProfileMapper.essentialMapper(optionalTA.get());
    }

    @Override
    public List<TAScheduleDTO> getTAScheduleById(DateIntervalDTO dateIntervalDTO, int id) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd"); //date format might be different
        LocalDate from = LocalDate.parse(dateIntervalDTO.getStartDate(), dtf);
        LocalDate to = LocalDate.parse(dateIntervalDTO.getEndDate(), dtf);
        Optional<TA> optionalTA = taRepository.findByUserId(id);

        List<TAScheduleDTO> schedule = new ArrayList<>();

        if (optionalTA.isEmpty()) {
            throw new RuntimeException("TA with ID " + id + " not found.");
        }

        Optional<Student> optionalStudent = studentRepo.findByBilkentId(optionalTA.get().getBilkentId());
        List<CourseStudentRelation> courseStudentRelations = optionalStudent.get().getCourseStudentRelations();

        //add schedule of ta's courses
        for (CourseStudentRelation courseStudentRelation : courseStudentRelations) {
            String name = courseStudentRelation.getCourse().getCourse().getDepartmentCourseCode() + " - " + courseStudentRelation.getCourse().getSectionNo();
            List<OfferedCourseScheduleRelation> courseSchedule = courseStudentRelation.getCourse().getSchedule();

            for (OfferedCourseScheduleRelation offeredCourseScheduleRelation : courseSchedule) {
                TimeInterval timeInterval = offeredCourseScheduleRelation.getTimeInterval();
                TAScheduleDTO scheduleDTO = TAScheduleMapper.essentialMapper(timeInterval, name);
                schedule.add(scheduleDTO);
            }
        }

        //add schedule of proctorings
        List<ClassProctoringTARelation> classProctorings = optionalTA.get().getClassProctoringTARelations();

        LocalDateTime fromDate = LocalDateTime.of(from.getYear(), from.getMonth(), from.getDayOfMonth(), 0, 0, 0);
        LocalDateTime toDate = LocalDateTime.of(to.getYear(), to.getMonth(), to.getDayOfMonth() + 1, 0, 0, 0);

        for (ClassProctoringTARelation classProctoringTARelation : classProctorings) {
            LocalDateTime eventStart = classProctoringTARelation.getClassProctoring().getStartDate();
            LocalDateTime eventEnd = classProctoringTARelation.getClassProctoring().getEndDate();

            if (eventStart.isAfter(fromDate) && eventStart.isBefore(toDate)) {
                TimeInterval proctoringInterval = new TimeInterval();
                proctoringInterval.setDay(eventStart.getDayOfWeek().toString());
                proctoringInterval.setStartTime(Time.valueOf(eventStart.getHour() + ":" + eventStart.getMinute() + ":00"));
                proctoringInterval.setEndTime(Time.valueOf(eventEnd.getHour() + ":" + eventEnd.getMinute() + ":00"));
                String eventName = classProctoringTARelation.getClassProctoring().getCourse().getDepartmentCourseCode() + " - " + classProctoringTARelation.getClassProctoring().getEventName();
                TAScheduleDTO scheduleDTO = TAScheduleMapper.essentialMapper(proctoringInterval, eventName);
                schedule.add(scheduleDTO);
            }
        }

        return schedule;
    }
}