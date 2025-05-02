package com.cs319group3.backend.DTOs;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentSecretaryProfileDTO {
    String name;
    String surname;
    String email;
    String bilkentId;
    String role;
    String departmentName;
    int departmentId;
    String phoneNumber;
    boolean active;
}
