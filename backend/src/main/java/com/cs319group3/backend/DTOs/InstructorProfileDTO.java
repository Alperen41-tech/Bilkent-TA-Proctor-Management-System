package com.cs319group3.backend.DTOs;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InstructorProfileDTO {
    String name;
    String surname;
    String email;
    String bilkentId;
    String role;
    String departmentName;
    List<String> courses;
}
