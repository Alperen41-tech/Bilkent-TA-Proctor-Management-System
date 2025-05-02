package com.cs319group3.backend.DTOs;

import com.cs319group3.backend.Entities.Login;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateInstructorDTO {
    InstructorProfileDTO instructorDTO;
    LoginDTO loginDTO;
}
