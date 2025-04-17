package com.cs319group3.backend.Entities;


import com.cs319group3.backend.Entities.RelationEntities.ClassProctoringTARelation;
import com.cs319group3.backend.Entities.UserEntities.Instructor;
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


    @ManyToOne
    @JoinColumn(name = "task_type_id")
    private TaskType taskType;

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private Instructor creatorInstructor;

    @OneToMany(mappedBy = "classProctoring")
    private List<ClassProctoringTARelation> TAsOfProctoring;

    @OneToMany(mappedBy = "classProctoring")
    private List<ClassProctoringClassroom> classrooms;
}
