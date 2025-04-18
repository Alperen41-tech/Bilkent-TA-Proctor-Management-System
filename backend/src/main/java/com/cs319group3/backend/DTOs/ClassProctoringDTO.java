package com.cs319group3.backend.DTOs;


import com.cs319group3.backend.Entities.ClassProctoring;
import com.cs319group3.backend.Entities.ClassProctoringClassroom;
import com.cs319group3.backend.Entities.Course;
import com.cs319group3.backend.Entities.TimeInterval;
import jdk.jfr.DataAmount;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClassProctoringDTO {



    private int id;
    private String proctoringName;
    private String courseName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String classrooms;

}
