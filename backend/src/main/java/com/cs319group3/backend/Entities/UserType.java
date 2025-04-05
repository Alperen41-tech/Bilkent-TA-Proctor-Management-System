package com.cs319group3.backend.Entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_type")
public class UserType {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userTypeId;


    private String userTypeName;

}
