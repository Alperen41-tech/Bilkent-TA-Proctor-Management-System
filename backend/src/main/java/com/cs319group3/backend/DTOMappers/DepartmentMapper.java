package com.cs319group3.backend.DTOMappers;

import com.cs319group3.backend.DTOs.DepartmentDTO;
import com.cs319group3.backend.Entities.Department;

public class DepartmentMapper {
    public static DepartmentDTO essentialMapper(Department department) {
        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setDepartmentId(department.getDepartmentId());
        departmentDTO.setDepartmentCode(department.getDepartmentCode());
        departmentDTO.setDepartmentName(department.getDepartmentName());
        return departmentDTO;
    }
}
