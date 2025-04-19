package com.cs319group3.backend.Entities.RelationEntities;

import com.cs319group3.backend.CompositeIDs.CourseInstructorKey;
import com.cs319group3.backend.Entities.OfferedCourse;
import com.cs319group3.backend.Entities.UserEntities.Instructor;
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
@Table(name = "course_instructor_relation")
public class CourseInstructorRelation{

    @EmbeddedId
    private CourseInstructorKey id;

    @ManyToOne
    @MapsId("offeredCourseId")
    @JoinColumn(name = "offered_course_id")
    private OfferedCourse course;

    @ManyToOne
    @MapsId("instructorId")
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;

}
