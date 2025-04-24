package com.cs319group3.backend.DTOs;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskTypeDTO {
    String courseName;
    String taskTypeName;
    int taskLimit;
}
