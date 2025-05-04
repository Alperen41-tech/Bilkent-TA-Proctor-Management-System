package com.cs319group3.backend.Entities;


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
@Table(name = "course")
public class Course {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int courseId;


    private String courseName;
    private int courseCode;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "coordinator_id")
    private Instructor coordinator;


    public String getCourseFullName() {
        return department.getDepartmentCode() + " " + courseCode;
    }


}
