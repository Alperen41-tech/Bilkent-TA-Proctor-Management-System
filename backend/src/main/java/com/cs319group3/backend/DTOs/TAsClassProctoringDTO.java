package com.cs319group3.backend.DTOs;


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
public class TAsClassProctoringDTO {



    int id;
    String name;
    Course course;
    LocalDateTime date;
    List<TimeInterval> timeInterval;
    String classrooms;

}
