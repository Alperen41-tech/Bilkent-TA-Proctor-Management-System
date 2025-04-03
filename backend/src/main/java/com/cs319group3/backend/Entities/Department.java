package com.cs319group3.backend.Entities;


import com.cs319group3.backend.Entities.UserEntities.Instructor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.jndi.JndiAccessor;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "department")
public class Department {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int departmentId;


    private String departmentName;

    private String departmentCode;


    @OneToOne
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;



    @OneToMany(mappedBy = "department")
    private List<Instructor> instructorList;














}
