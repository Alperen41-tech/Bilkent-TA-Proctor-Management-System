package com.cs319group3.backend.DTOs;


import com.cs319group3.backend.Entities.ClassProctoring;
import com.cs319group3.backend.Entities.ClassProctoringClassroom;
import com.cs319group3.backend.Entities.Course;
import com.cs319group3.backend.Entities.TimeInterval;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClassProctoringDTO {



    int id;
    String name;
    Course course;
    LocalDateTime startDate;
    LocalDateTime endDate;
    String classrooms;






}
