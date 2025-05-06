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
    private TimeIntervalService timeIntervalService;

    @Autowired
    private TALeaveRequestRepo taleaveRequestRepo;
    @Autowired
    private ClassProctoringTARelationRepo classProctoringTARelationRepo;
    @Autowired
    private StudentRepo studentRepo;

    @Override
    public boolean isTAAvailable(TA ta, ClassProctoring otherCtr){

        /* ta is not available
         * if he has another proctoring in the time interval
         * if he has singed as with leave of absence
         * if he has lecture
         * if he already recevied a swap reeqeust about that */

        LocalDateTime startTime = otherCtr.getStartDate();
        LocalDateTime endTime = otherCtr.getEndDate();


        return !(isLeft(ta, startTime, endTime)
                || hasLecture(ta, startTime, endTime)
                || hasAnotherProctoring(ta, startTime, endTime)) ;
    }

    private boolean isLeft(TA ta,  LocalDateTime startTime, LocalDateTime endTime){

        List<TALeaveRequest> approvedRequest = taleaveRequestRepo.findBySenderUser_UserIdAndApprovedTrueAndLeaveStartDateLessThanEqualAndLeaveEndDateGreaterThanEqual(ta.getUserId(), endTime, startTime);
        return !approvedRequest.isEmpty();
    }

    private boolean hasAnotherProctoring(TA ta, LocalDateTime startTime, LocalDateTime endTime){
        List<ClassProctoringTARelation> relations = classProctoringTARelationRepo.findByTA_UserIdAndClassProctoring_StartDateLessThanEqualAndClassProctoring_EndDateGreaterThan(ta.getUserId(), endTime, startTime);
        return !relations.isEmpty();
    }

    private boolean hasLecture(TA ta, LocalDateTime startTime, LocalDateTime endTime){
        Optional<Student> student = studentRepo.findByBilkentId(ta.getBilkentId());
        if (!student.isPresent()){
            return false;
        }

        Student studentReceived = student.get();
        List<CourseStudentRelation> relations = studentReceived.getCourseStudentRelations();

        for (CourseStudentRelation relation : relations){
            OfferedCourse c = relation.getCourse();
            if (hasCourseIntersect(c, startTime, endTime))
                return true;
        }

        return false;
    }

    private boolean hasCourseIntersect(OfferedCourse c, LocalDateTime start, LocalDateTime end) {
        List<OfferedCourseScheduleRelation> relations = c.getSchedule();

        for (OfferedCourseScheduleRelation relation : relations) {
            TimeInterval timeInterval = relation.getTimeInterval();
            String dayOfWeek = timeInterval.getDay(); // Assuming this gets the day as string (e.g., "MONDAY")

            // Get the day of week from start and end DateTimes
            String startDayOfWeek = start.getDayOfWeek().toString();
            String endDayOfWeek = end.getDayOfWeek().toString();

            // Check if the day matches
            if (dayOfWeek.equalsIgnoreCase(startDayOfWeek)) {
                // Get time parts from the interval
                LocalTime intervalStart = timeInterval.getStartTime();
                LocalTime intervalEnd = timeInterval.getEndTime();

                // Get time parts from parameters
                LocalTime startTime = start.toLocalTime();
                LocalTime endTime = end.toLocalTime();

                // Check for time overlap
                // Two time intervals overlap if one starts before the other ends and ends after the other starts
                if (intervalEnd.isAfter(startTime) && intervalStart.isBefore(endTime)) {
                    return true; // There is an intersection
                }
            }
        }

        return false; // No intersection found
    }
}
