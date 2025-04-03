package com.cs319group3.backend.Entities;


import com.cs319group3.backend.Entities.UserEntities.DeansOffice;
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
@Table(name = "faculty")
public class Faculty {




    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int facultyId;

    private String facultyName;

    @OneToOne(mappedBy = "faculty")
    private DeansOffice deansOffice;

}
