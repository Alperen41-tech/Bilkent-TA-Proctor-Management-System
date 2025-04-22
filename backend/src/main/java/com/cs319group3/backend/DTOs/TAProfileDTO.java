package com.cs319group3.backend.DTOs;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TAProfileDTO {
    String name;
    String surname;
    String email;
    String bilkentId;
    String role;
    String departmentName;
    String courseName;
    String phoneNumber;
    boolean active;
    int classYear;
}
