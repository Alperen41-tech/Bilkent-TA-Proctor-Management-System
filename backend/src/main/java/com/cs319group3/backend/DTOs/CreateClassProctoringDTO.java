package com.cs319group3.backend.DTOs;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateClassProctoringDTO {
    private int id;
    private String courseName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<String> classrooms;
    private int taCount;
    private int sectionNo;
    private String eventName;
}
