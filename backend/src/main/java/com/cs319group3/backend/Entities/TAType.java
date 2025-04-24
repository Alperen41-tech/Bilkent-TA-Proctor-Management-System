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
@Table(name = "ta_type")
public class TAType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int taTypeId;

    private String typeName;

    private int taLoad;

}
