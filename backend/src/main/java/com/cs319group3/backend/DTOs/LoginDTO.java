package com.cs319group3.backend.DTOs;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class LoginDTO {
    private String email;
    private String password;
}
