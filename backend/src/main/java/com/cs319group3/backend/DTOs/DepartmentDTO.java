package com.cs319group3.backend.DTOs;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDTO {
    private int departmentId;
    private String departmentCode;
    private String departmentName;
}
