package com.cs319group3.backend.Services;

import com.cs319group3.backend.Entities.ClassProctoring;
import com.cs319group3.backend.Entities.OfferedCourse;
import com.cs319group3.backend.Entities.UserEntities.TA;

import java.time.LocalDateTime;

public interface TAAvailabilityService {

    /**
     * Checks if a TA is available during the given class proctoring.
     *
     * @param ta       The teaching assistant to check.
     * @param otherCtr The class proctoring to compare against.
     * @return true if the TA is available, false otherwise.
     */
    boolean isTAAvailable(TA ta, ClassProctoring otherCtr);

    /**
     * Checks if the TA is available during a specific time window due to having left the university.
     *
     * @param ta        The teaching assistant.
     * @param startTime Start of the time window.
     * @param endTime   End of the time window.
     * @return true if the TA has left during this time, false otherwise.
     */
    boolean isLeft(TA ta, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * Checks if the TA has another proctoring session that overlaps with the given time interval.
     *
     * @param ta        The teaching assistant.
     * @param startTime Start of the time interval.
     * @param endTime   End of the time interval.
     * @return true if there is a conflict with another proctoring, false otherwise.
     */
    boolean hasAnotherProctoring(TA ta, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * Checks if the TA has a lecture that overlaps with the given time interval.
     *
     * @param ta        The teaching assistant.
     * @param startTime Start of the time interval.
     * @param endTime   End of the time interval.
     * @return true if the TA has a lecture at this time, false otherwise.
     */
    boolean hasLecture(TA ta, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * Checks if a course overlaps with a given time interval.
     *
     * @param c     The offered course.
     * @param start Start of the interval.
     * @param end   End of the interval.
     * @return true if the course intersects with the interval, false otherwise.
     */
    boolean hasCourseIntersect(OfferedCourse c, LocalDateTime start, LocalDateTime end);
}