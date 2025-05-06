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
    private LocalDateTime finalDate;

    @Column(name = "min_ta_load")
    private int minTALoad;
    @Column(name = "max_ta_load")
    private int maxTALoad;
    private int numberOfGrader;

    private String description;

    @ManyToOne
    @JoinColumn(name = "coordinator_id")
    private Instructor coordinator;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "semester")
    private Semester semester;

    @ManyToMany
    @JoinTable(
            name = "must_have_tas",
            joinColumns = @JoinColumn(name = "form_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<TA> mustHaveTAs;


    @ManyToMany
    @JoinTable(
            name = "preferred_tas",
            joinColumns = @JoinColumn(name = "form_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @OrderColumn(name = "preference_rank")  // This creates an additional column to maintain order
    private List<TA> preferredTAs;


    @ManyToMany
    @JoinTable(
            name = "preferred_graders",
            joinColumns = @JoinColumn(name = "form_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @OrderColumn(name = "preference_rank")  // This creates an additional column to maintain order
    private List<TA> preferredGraders;

    @ManyToMany
    @JoinTable(
            name = "unwantedTAs",
            joinColumns = @JoinColumn(name = "form_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<TA> unwantedTAs;










}
