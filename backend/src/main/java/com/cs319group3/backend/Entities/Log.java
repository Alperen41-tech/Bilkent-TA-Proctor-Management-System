package com.cs319group3.backend.Entities;

import com.cs319group3.backend.Enums.LogType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "log")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int log_id;

    private String message;

    @Enumerated(EnumType.STRING)
    private LogType logType;

    private LocalDateTime logDate;
}
