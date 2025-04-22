package com.cs319group3.backend.DTOs;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTADTO {
    //User
    TAProfileDTO profile;
    //Login
    LoginDTO login;
}
