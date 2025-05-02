package com.cs319group3.backend.DTOs;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateDepartmentSecretaryDTO {
    DepartmentSecretaryProfileDTO profile;
    LoginDTO login;
}
