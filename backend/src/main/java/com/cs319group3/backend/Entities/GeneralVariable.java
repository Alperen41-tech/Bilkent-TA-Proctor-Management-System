package com.cs319group3.backend.Entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "general_variable")
public class GeneralVariable {

    //TODO in general variable table, general variable id columns should be defined

    // TODO entity base diye değiştirilecek

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int generalVariableId;

    private int TAProctoringCapTime;

    @OneToOne
    @JoinColumn(name = "semester_id")
    private Semester semester;

}
