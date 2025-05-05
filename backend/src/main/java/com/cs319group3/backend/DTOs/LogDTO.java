package com.cs319group3.backend.DTOs;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LogDTO {
    String message;
    String logType;
    LocalDateTime logDate;
}
