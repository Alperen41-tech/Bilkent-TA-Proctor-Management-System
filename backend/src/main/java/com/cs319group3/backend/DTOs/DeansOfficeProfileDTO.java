package com.cs319group3.backend.DTOs;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeansOfficeProfileDTO {
    String name;
    String surname;
    String email;
    String bilkentId;
    String role;
    String faculty;
    String phoneNumber;
    boolean active;
    int facultyId;
}
