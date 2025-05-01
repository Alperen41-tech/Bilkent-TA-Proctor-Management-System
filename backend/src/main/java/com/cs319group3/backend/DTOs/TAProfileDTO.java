package com.cs319group3.backend.DTOs;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TAProfileDTO {
    private int userId;
    private String name;
    private String surname;
    private String email;
    private String bilkentId;
    private String role;
    private String departmentName;
    private String courseName;
    private String phoneNumber;
    private Boolean active;
    private int classYear;
}
