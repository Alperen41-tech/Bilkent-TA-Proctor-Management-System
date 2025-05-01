package com.cs319group3.backend.Services.ServiceImpls;

import com.cs319group3.backend.DTOMappers.TimeIntervalMapper;
import com.cs319group3.backend.DTOs.DateIntervalDTO;
import com.cs319group3.backend.DTOs.TimeIntervalDTO;
import com.cs319group3.backend.Entities.ClassProctoring;
import com.cs319group3.backend.Entities.RelationEntities.ClassProctoringTARelation;
import com.cs319group3.backend.Entities.RelationEntities.CourseInstructorRelation;
import com.cs319group3.backend.Entities.RelationEntities.CourseStudentRelation;
import com.cs319group3.backend.Entities.RelationEntities.OfferedCourseScheduleRelation;
import com.cs319group3.backend.Entities.RequestEntities.TALeaveRequest;
import com.cs319group3.backend.Entities.Student;
import com.cs319group3.backend.Entities.TimeInterval;
import com.cs319group3.backend.Entities.UserEntities.Instructor;
import com.cs319group3.backend.Entities.UserEntities.TA;
import com.cs319group3.backend.Repositories.*;
import com.cs319group3.backend.Services.TimeIntervalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TimeIntervalServiceImpl implements TimeIntervalService {

    @Autowired
    private TARepo taRepository;

    @Autowired
    private InstructorRepo instructorRepo;

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private ClassProctoringRepo classProctoringRepo;

    @Override
    public List<TimeIntervalDTO> getTAScheduleById(DateIntervalDTO dateIntervalDTO, int id) {
        Optional<TA> optionalTA = taRepository.findByUserId(id);

        if (optionalTA.isEmpty()) {
            throw new RuntimeException("TA with ID " + id + " not found.");
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd"); //date format might be different
        LocalDate from = LocalDate.parse(dateIntervalDTO.getStartDate(), dtf);
        LocalDate to = LocalDate.parse(dateIntervalDTO.getEndDate(), dtf);
        LocalDateTime fromDate = LocalDateTime.of(from.getYear(), from.getMonth(), from.getDayOfMonth(), 0, 0, 0);
        LocalDateTime toDate = LocalDateTime.of(to.getYear(), to.getMonth(), to.getDayOfMonth() + 1, 0, 0, 0);

        List<TimeIntervalDTO> schedule = new ArrayList<>();

        //add leave of absence
        int scheduleStart = 8;
        int scheduleEnd = 22;
        List<TALeaveRequest> requests = optionalTA.get().getTAAvailabilityRequests();
        requests.removeIf(tAAvailabilityRequest -> tAAvailabilityRequest.getIsApproved() == null || !tAAvailabilityRequest.getIsApproved());
        addLeaveOfAbsence(scheduleStart, scheduleEnd, schedule, requests, fromDate, toDate);

        //add schedule of ta's courses
        Optional<Student> optionalStudent = studentRepo.findByBilkentId(optionalTA.get().getBilkentId());
        List<CourseStudentRelation> courseStudentRelations = optionalStudent.get().getCourseStudentRelations();
        addTaCourses(courseStudentRelations, schedule);

        //TODO Optional objects may not exist in the database

        //add schedule of proctorings
        List<ClassProctoringTARelation> classProctorings = optionalTA.get().getClassProctoringTARelations();
        addTAProctorings(classProctorings, fromDate, toDate, schedule);

        return schedule;
    }

    @Override
    public List<TimeIntervalDTO> getInstructorScheduleById(DateIntervalDTO dateIntervalDTO, int id) {
        Optional<Instructor> optionalInstructor = instructorRepo.findByUserId(id);

        if (optionalInstructor.isEmpty()) {
            throw new RuntimeException("Instructor with ID " + id + " not found.");
        }

        List<TimeIntervalDTO> schedule = new ArrayList<>();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd"); //date format might be different
        LocalDate from = LocalDate.parse(dateIntervalDTO.getStartDate(), dtf);
        LocalDate to = LocalDate.parse(dateIntervalDTO.getEndDate(), dtf);
        LocalDateTime fromDate = LocalDateTime.of(from.getYear(), from.getMonth(), from.getDayOfMonth(), 0, 0, 0);
        LocalDateTime toDate = LocalDateTime.of(to.getYear(), to.getMonth(), to.getDayOfMonth() + 1, 0, 0, 0);

        //add schedule of courses
        List<CourseInstructorRelation> courses = optionalInstructor.get().getCourseInstructorRelations();
        addInstructorCourses(courses, schedule);

        //add schedule of proctorings
        addInstructorProctorings(courses, fromDate, toDate, schedule);

        return schedule;
    }


    private void addLeaveOfAbsence(int scheduleStart, int scheduleEnd, List<TimeIntervalDTO> schedule, List<TALeaveRequest> requests, LocalDateTime fromDate, LocalDateTime toDate) {
        for (TALeaveRequest tAAvailabilityRequest : requests) {
            LocalDateTime availabilityStart = tAAvailabilityRequest.getLeaveStartDate();
            LocalDateTime availabilityEnd = tAAvailabilityRequest.getLeaveEndDate();
            if (availabilityStart.isBefore(toDate) && availabilityEnd.isAfter(fromDate)) {
                LocalDateTime start = availabilityStart;
                if (start.getHour() < scheduleStart)
                    start = LocalDateTime.of(start.getYear(), start.getMonth(), start.getDayOfMonth(), scheduleStart, 0, 0);
                if (start.getHour() >= scheduleEnd) {
                    start = LocalDateTime.of(start.getYear(), start.getMonth(), start.getDayOfMonth(), scheduleStart, 0, 0);
                    start = start.plusDays(1);
                }
                while (start.isBefore(toDate)) {
                    int endHour = scheduleEnd;
                    int endMinute = 0;
                    boolean isEnd = false;
                    if (start.getHour() < scheduleStart)
                        start = LocalDateTime.of(start.getYear(), start.getMonth(), start.getDayOfMonth(), scheduleStart, 0, 0);
                    if (availabilityEnd.isBefore(LocalDateTime.of(start.getYear(), start.getMonth(), start.getDayOfMonth(), scheduleEnd, 0, 0))) {
                        endHour = availabilityEnd.getHour();
                        endMinute = availabilityEnd.getMinute();
                        isEnd = true;
                        if (availabilityEnd.isBefore(LocalDateTime.of(start.getYear(), start.getMonth(), start.getDayOfMonth(), scheduleStart, 0, 0)))
                            break;
                    }
                    TimeInterval timeInterval = new TimeInterval();
                    timeInterval.setDay(start.getDayOfWeek().toString());
                    timeInterval.setStartTime(start.toLocalTime());
                    timeInterval.setEndTime(LocalTime.of(endHour, endMinute));
                    TimeIntervalDTO timeIntervalDTO = TimeIntervalMapper.essentialMapper(timeInterval, "leave of absence", "Leave of Absence");
                    schedule.add(timeIntervalDTO);
                    if (isEnd)
                        break;
                    start = LocalDateTime.of(start.getYear(), start.getMonth(), start.getDayOfMonth(), scheduleStart, 0, 0);
                    start = start.plusDays(1);
                }
            }
        }
    }

    private void addTaCourses(List<CourseStudentRelation> courseStudentRelations, List<TimeIntervalDTO> schedule) {
        for (CourseStudentRelation courseStudentRelation : courseStudentRelations) {
            String name = courseStudentRelation.getCourse().getCourse().getDepartmentCourseCode() + " - " + courseStudentRelation.getCourse().getSectionNo();
            List<OfferedCourseScheduleRelation> courseSchedule = courseStudentRelation.getCourse().getSchedule();
            for (OfferedCourseScheduleRelation offeredCourseScheduleRelation : courseSchedule) {
                TimeInterval timeInterval = offeredCourseScheduleRelation.getTimeInterval();
                boolean isOverlap = false;
                for (TimeIntervalDTO timeIntervalDTO : schedule ) {
                    LocalTime absenceStart = LocalTime.of(Integer.parseInt(timeIntervalDTO.getStartTime().substring(0,2)), Integer.parseInt(timeIntervalDTO.getStartTime().substring(3)));
                    LocalTime absenceEnd = LocalTime.of(Integer.parseInt(timeIntervalDTO.getEndTime().substring(0,2)), Integer.parseInt(timeIntervalDTO.getEndTime().substring(3)));
                    if (timeInterval.getDay().equalsIgnoreCase(timeIntervalDTO.getDayOfWeek())) {
                        if (absenceStart.isBefore(timeInterval.getStartTime()) && absenceEnd.isAfter(timeInterval.getEndTime())) {
                            isOverlap = true;
                            break;
                        }
                        if (absenceStart.isBefore(timeInterval.getStartTime()) && absenceEnd.isBefore(timeInterval.getEndTime()) && absenceEnd.isAfter(timeInterval.getStartTime())) {
                            timeInterval.setStartTime(absenceEnd);
                            continue;
                        }
                        if (absenceStart.isAfter(timeInterval.getStartTime()) && absenceStart.isBefore(timeInterval.getEndTime()) && absenceEnd.isAfter(timeInterval.getEndTime()))
                            timeInterval.setEndTime(absenceStart);
                    }
                }
                if (isOverlap)
                    continue;
                TimeIntervalDTO timeIntervalDTO = TimeIntervalMapper.essentialMapper(timeInterval, "lecture", name);
                schedule.add(timeIntervalDTO);
            }
        }
    }

    private void addTAProctorings(List<ClassProctoringTARelation> classProctorings, LocalDateTime fromDate, LocalDateTime toDate, List<TimeIntervalDTO> schedule) {
        for (ClassProctoringTARelation classProctoringTARelation : classProctorings) {
            LocalDateTime eventStart = classProctoringTARelation.getClassProctoring().getStartDate();
            LocalDateTime eventEnd = classProctoringTARelation.getClassProctoring().getEndDate();
            if (eventStart.isAfter(fromDate) && eventStart.isBefore(toDate)) {
                TimeInterval proctoringInterval = new TimeInterval();
                proctoringInterval.setDay(eventStart.getDayOfWeek().toString());
                proctoringInterval.setStartTime(eventStart.toLocalTime());
                proctoringInterval.setEndTime(eventEnd.toLocalTime());
                String eventName = classProctoringTARelation.getClassProctoring().getCourse().getDepartmentCourseCode() + " - " + classProctoringTARelation.getClassProctoring().getEventName();
                TimeIntervalDTO timeIntervalDTO = TimeIntervalMapper.essentialMapper(proctoringInterval, "proctoring", eventName);
                schedule.add(timeIntervalDTO);
            }
        }
    }

    private void addInstructorCourses(List<CourseInstructorRelation> courses, List<TimeIntervalDTO> schedule) {
        for (CourseInstructorRelation course : courses) {
            String name = course.getCourse().getCourse().getDepartmentCourseCode() + " - " + course.getCourse().getSectionNo();
            List<OfferedCourseScheduleRelation> courseSchedule = course.getCourse().getSchedule();
            for (OfferedCourseScheduleRelation offeredCourseScheduleRelation : courseSchedule) {
                TimeInterval timeInterval = offeredCourseScheduleRelation.getTimeInterval();
                TimeIntervalDTO timeIntervalDTO = TimeIntervalMapper.essentialMapper(timeInterval, "lecture", name);
                schedule.add(timeIntervalDTO);
            }
        }
    }

    private void addInstructorProctorings(List<CourseInstructorRelation> courses, LocalDateTime fromDate, LocalDateTime toDate, List<TimeIntervalDTO> schedule){
        for(CourseInstructorRelation course : courses) {
            int courseId = course.getCourse().getCourse().getCourseId();
            List<ClassProctoring> proctorings = classProctoringRepo.findByCourse_CourseId(courseId);
            for (ClassProctoring classProctoring : proctorings) {
                LocalDateTime eventStart = classProctoring.getStartDate();
                LocalDateTime eventEnd = classProctoring.getEndDate();
                if (eventStart.isAfter(fromDate) && eventStart.isBefore(toDate)) {
                    TimeInterval proctoringInterval = new TimeInterval();
                    proctoringInterval.setDay(eventStart.getDayOfWeek().toString());
                    proctoringInterval.setStartTime(eventStart.toLocalTime());
                    proctoringInterval.setEndTime(eventEnd.toLocalTime());
                    String eventName = classProctoring.getCourse().getDepartmentCourseCode() + " - " + classProctoring.getEventName();
                    TimeIntervalDTO timeIntervalDTO = TimeIntervalMapper.essentialMapper(proctoringInterval, "proctoring", eventName);
                    schedule.add(timeIntervalDTO);
                }
            }
        }
    }

}
