package com.cs319group3.backend.Services.ServiceImpls;

import com.cs319group3.backend.Entities.ClassProctoring;
import com.cs319group3.backend.Entities.OfferedCourse;
import com.cs319group3.backend.Entities.RelationEntities.ClassProctoringTARelation;
import com.cs319group3.backend.Entities.RelationEntities.CourseStudentRelation;
import com.cs319group3.backend.Entities.RelationEntities.OfferedCourseScheduleRelation;
import com.cs319group3.backend.Entities.RequestEntities.TALeaveRequest;
import com.cs319group3.backend.Entities.Student;
import com.cs319group3.backend.Entities.TimeInterval;
import com.cs319group3.backend.Entities.UserEntities.TA;
import com.cs319group3.backend.Repositories.ClassProctoringTARelationRepo;
import com.cs319group3.backend.Repositories.StudentRepo;
import com.cs319group3.backend.Repositories.TALeaveRequestRepo;
import com.cs319group3.backend.Services.TAAvailabilityService;
import com.cs319group3.backend.Services.TimeIntervalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class TAAvailabilityServiceImpl implements TAAvailabilityService {

    @Autowired
    private TALeaveRequestRepo taleaveRequestRepo;

    @Autowired
    private ClassProctoringTARelationRepo classProctoringTARelationRepo;

    @Autowired
    private StudentRepo studentRepo;

    @Override
    public boolean isTAAvailable(TA ta, ClassProctoring otherCtr) {
        LocalDateTime startTime = otherCtr.getStartDate();
        LocalDateTime endTime = otherCtr.getEndDate();

        return !(isLeft(ta, startTime, endTime)
                || hasLecture(ta, startTime, endTime)
                || hasAnotherProctoring(ta, startTime, endTime));
    }

    @Override
    public boolean isLeft(TA ta, LocalDateTime startTime, LocalDateTime endTime) {
        List<TALeaveRequest> approvedRequest =
                taleaveRequestRepo.findBySenderUser_UserIdAndApprovedTrueAndLeaveStartDateLessThanEqualAndLeaveEndDateGreaterThanEqual(
                        ta.getUserId(), endTime, startTime);
        return !approvedRequest.isEmpty();
    }

    @Override
    public boolean hasAnotherProctoring(TA ta, LocalDateTime startTime, LocalDateTime endTime) {
        List<ClassProctoringTARelation> relations =
                classProctoringTARelationRepo.findByTA_UserIdAndClassProctoring_StartDateLessThanEqualAndClassProctoring_EndDateGreaterThan(
                        ta.getUserId(), endTime, startTime);
        return !relations.isEmpty();
    }

    @Override
    public boolean hasLecture(TA ta, LocalDateTime startTime, LocalDateTime endTime) {
        Optional<Student> student = studentRepo.findByBilkentId(ta.getBilkentId());
        if (!student.isPresent()) {
            return false;
        }

        Student studentReceived = student.get();
        List<CourseStudentRelation> relations = studentReceived.getCourseStudentRelations();

        for (CourseStudentRelation relation : relations) {
            OfferedCourse c = relation.getCourse();
            if (hasCourseIntersect(c, startTime, endTime)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean hasCourseIntersect(OfferedCourse c, LocalDateTime start, LocalDateTime end) {
        List<OfferedCourseScheduleRelation> relations = c.getSchedule();

        for (OfferedCourseScheduleRelation relation : relations) {
            TimeInterval timeInterval = relation.getTimeInterval();
            String dayOfWeek = timeInterval.getDay();

            String startDayOfWeek = start.getDayOfWeek().toString();
            String endDayOfWeek = end.getDayOfWeek().toString();

            if (dayOfWeek.equalsIgnoreCase(startDayOfWeek)) {
                LocalTime intervalStart = timeInterval.getStartTime();
                LocalTime intervalEnd = timeInterval.getEndTime();

                LocalTime startTime = start.toLocalTime();
                LocalTime endTime = end.toLocalTime();

                if (intervalEnd.isAfter(startTime) && intervalStart.isBefore(endTime)) {
                    return true;
                }
            }
        }

        return false;
    }
}