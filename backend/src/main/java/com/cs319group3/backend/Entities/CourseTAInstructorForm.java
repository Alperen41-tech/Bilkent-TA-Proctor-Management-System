package com.cs319group3.backend.Entities;


import com.cs319group3.backend.Entities.UserEntities.Instructor;
import com.cs319group3.backend.Entities.UserEntities.TA;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "course_ta_instructor_form")
public class CourseTAInstructorForm {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int formId;

    private LocalDateTime sentDate;

    @Column(name = "min_ta_load")
    private int minTALoad;
    @Column(name = "max_ta_load")
    private int maxTALoad;
    private int numberOfGrader;

    private String description;

    @Column(name = "must_have_tas")
    private String mustHaveTAs;

    @Column(name = "preferred_tas")
    private String preferredTAs;

    private String preferredGraders;

    @Column(name = "avoided_tas")
    private String avoidedTAs;

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "semester_id")
    private Semester semester;
}
