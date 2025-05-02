package com.cs319group3.backend.DTOs;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordDTO {

    private String email;
    private String oldPassword;
    private String newPassword;
    private String userTypeName;

}

