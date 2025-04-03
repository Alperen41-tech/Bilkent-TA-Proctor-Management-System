package com.cs319group3.backend.Entities;


import com.cs319group3.backend.Entities.UserEntities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.mapping.Join;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_type")
public class UserType {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userTypeID;


    private String userTypeName;


}
