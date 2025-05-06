package com.cs319group3.backend.Services;

import com.cs319group3.backend.Entities.ClassProctoring;
import com.cs319group3.backend.Entities.OfferedCourse;
import com.cs319group3.backend.Entities.UserEntities.TA;

import java.time.LocalDateTime;

public interface TAAvailabilityService {
    public boolean isTAAvailable(TA ta, ClassProctoring otherCtr);
    public boolean isLeft(TA ta, LocalDateTime startTime, LocalDateTime endTime);
    public boolean hasAnotherProctoring(TA ta, LocalDateTime startTime, LocalDateTime endTime);
    public boolean hasLecture(TA ta, LocalDateTime startTime, LocalDateTime endTime);
    public boolean hasCourseIntersect(OfferedCourse c, LocalDateTime start, LocalDateTime end);
}
