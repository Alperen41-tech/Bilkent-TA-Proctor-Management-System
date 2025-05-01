package com.cs319group3.backend.Entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "proctoring_application")
public class ProctoringApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int applicationId;

    @ManyToOne
    @JoinColumn(name = "class_proctoring_id")
    private ClassProctoring classProctoring;

    @ManyToOne
    @JoinColumn(name = "visible_department_id")
    private Department visibleDepartment;

    private int applicantCountLimit;

    private boolean isComplete;

    @Column(name = "is_visible_for_tas")
    private boolean isVisibleForTAs;

    private LocalDateTime finishDate;
}
