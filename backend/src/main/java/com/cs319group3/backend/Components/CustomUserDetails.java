package com.cs319group3.backend.Components;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class CustomUserDetails extends User {

    private String userType;
    private int userId;
    private String bilkentId;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;

    // Constructor to initialize both CustomUserDetails and User class fields
    public CustomUserDetails(String username, String password, String userType, int userId,
                             String bilkentId, String name, String surname,
                             String email, String phoneNumber, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);  // Call the User constructor to set username, password, and authorities
        this.userType = userType;
        this.userId = userId;
        this.bilkentId = bilkentId;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
