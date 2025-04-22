package com.cs319group3.backend.Entities;


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
@Table(name = "task_type")
public class TaskType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int taskTypeId;

    private String taskTypeName;

    private int timeLimit;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

}
