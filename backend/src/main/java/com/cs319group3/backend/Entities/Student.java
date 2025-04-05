package com.cs319group3.backend.Entities;


import com.cs319group3.backend.Entities.RelationEntities.CourseStudentRelation;
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
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int studentId;


    private String bilkentId;

    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private boolean isActive;

    @Column(name = "class")
    private int classYear;

    @OneToMany(mappedBy = "student")
    private List<CourseStudentRelation> courseStudentRelations;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;



}
