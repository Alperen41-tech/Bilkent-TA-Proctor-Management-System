package com.cs319group3.backend.DTOs;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTADTO {
    //User
    private TAProfileDTO profile;
    //Login
    private LoginDTO login;
}
