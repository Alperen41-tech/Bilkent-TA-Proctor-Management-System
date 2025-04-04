package com.cs319group3.backend.Entities;

import com.cs319group3.backend.Entities.RelationEntities.CourseInstructorRelation;
import com.cs319group3.backend.Entities.RelationEntities.CourseStudentRelation;
import com.cs319group3.backend.Entities.RelationEntities.OfferedCourseScheduleRelation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "offered_course")
public class OfferedCourse {




    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int offeredCourseId;

    private int sectionNo;


    @OneToMany(mappedBy = "course")
    private List<CourseInstructorRelation> instructors;

    @OneToMany(mappedBy = "course")
    private List<OfferedCourseScheduleRelation> schedule;

    @OneToMany(mappedBy = "course")
    private List<CourseStudentRelation> courseStudentRelations;


    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "semester_id")
    private Semester semester;




}
