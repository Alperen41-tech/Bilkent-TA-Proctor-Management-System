package com.cs319group3.backend.DTOs;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateDeansOfficeDTO {
    DeansOfficeProfileDTO profile;
    LoginDTO login;
}
