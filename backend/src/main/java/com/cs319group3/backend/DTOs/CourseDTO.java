package com.cs319group3.backend.DTOs;


import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseDTO {

    private int id;
    private String name;
    private String description;
    private int courseCode;
    private String departmentCode;
    private int departmentId;
    private int coordinatorId;
}
