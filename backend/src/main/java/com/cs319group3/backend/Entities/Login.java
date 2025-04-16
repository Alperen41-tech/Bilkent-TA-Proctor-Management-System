package com.cs319group3.backend.Entities;


import com.cs319group3.backend.Entities.UserEntities.User;
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
@Table(name = "login")
public class Login {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int loginId;


    private String password;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;


    @ManyToOne
    @JoinColumn(name = "user_type_id")
    private UserType userType;
}
