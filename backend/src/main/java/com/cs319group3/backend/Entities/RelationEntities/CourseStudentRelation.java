package com.cs319group3.backend.Entities.RelationEntities;

import com.cs319group3.backend.CompositeIDs.CourseStudentKey;
import com.cs319group3.backend.Entities.OfferedCourse;
import com.cs319group3.backend.Entities.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "course_student_relation")
public class CourseStudentRelation {


    @EmbeddedId
    private CourseStudentKey id;


    @ManyToOne
    @MapsId("offeredCourseId")
    @JoinColumn(name = "offered_course_id")
    private OfferedCourse course;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    private Student student;

}
