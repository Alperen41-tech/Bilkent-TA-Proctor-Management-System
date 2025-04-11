package com.cs319group3.backend.Entities.UserEntities;


import com.cs319group3.backend.Entities.Department;
import com.cs319group3.backend.Entities.UserType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public abstract class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    private int bilkentId;

    private String name;

    private String surname;

    private String mail;

    private String phoneNumber;
    
    private boolean isActive;
}
