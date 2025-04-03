package com.cs319group3.backend.Entities.RelationEntities;


import com.cs319group3.backend.CompositeIDs.OfferedCourseScheduleKey;
import com.cs319group3.backend.Entities.OfferedCourse;
import com.cs319group3.backend.Entities.TimeInterval;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "offered_course_schedule_relation")
public class OfferedCourseScheduleRelation {


    @EmbeddedId
    private OfferedCourseScheduleKey id;


    @ManyToOne
    @MapsId("offeredCourseId")
    private OfferedCourse course;

    @ManyToOne
    @MapsId("timeIntervalId")
    private TimeInterval timeInterval;

}
