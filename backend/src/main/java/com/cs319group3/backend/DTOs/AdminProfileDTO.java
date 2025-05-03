package com.cs319group3.backend.DTOs;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminProfileDTO {
    int userId;
    String bilkentId;
    String name;
    String surname;
    String email;
    String phoneNumber;
    boolean active;
}
