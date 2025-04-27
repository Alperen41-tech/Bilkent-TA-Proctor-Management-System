package com.cs319group3.backend.Entities;


import com.cs319group3.backend.Entities.RelationEntities.ClassProctoringTARelation;
import com.cs319group3.backend.Entities.UserEntities.Instructor;
import com.cs319group3.backend.Entities.UserEntities.User;
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
@Table(name = "class_proctoring")
public class ClassProctoring {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int classProctoringId;


    private int sectionNo;

    @Column(name = "ta_count")
    private int TACount;

    private boolean isComplete;

    private LocalDateTime startDate;
    private LocalDateTime endDate;


    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    private String eventName;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @OneToMany(mappedBy = "classProctoring")
    private List<ClassProctoringTARelation> TAsOfProctoring;

    @OneToMany(mappedBy = "classProctoring", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClassProctoringClassroom> classrooms;


    @OneToMany(mappedBy = "classProctoring")
    private List<ProctoringApplication> applications;



}
