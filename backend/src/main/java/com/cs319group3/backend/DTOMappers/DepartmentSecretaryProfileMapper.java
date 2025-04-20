package com.cs319group3.backend.DTOMappers;

import com.cs319group3.backend.DTOs.DepartmentSecretaryProfileDTO;
import com.cs319group3.backend.Entities.UserEntities.DepartmentSecretary;

public class DepartmentSecretaryProfileMapper {
    public static DepartmentSecretaryProfileDTO essentialMapper(DepartmentSecretary departmentSecretary){
        DepartmentSecretaryProfileDTO departmentSecretaryProfileDTO = new DepartmentSecretaryProfileDTO();
        departmentSecretaryProfileDTO.setName(departmentSecretary.getName());
        departmentSecretaryProfileDTO.setSurname(departmentSecretary.getSurname());
        departmentSecretaryProfileDTO.setEmail(departmentSecretary.getEmail());
        departmentSecretaryProfileDTO.setBilkentId(departmentSecretary.getBilkentId());
        departmentSecretaryProfileDTO.setRole("Department Secretary");
        departmentSecretaryProfileDTO.setDepartmentName(departmentSecretary.getDepartment().getDepartmentName());
        return departmentSecretaryProfileDTO;
    }
}
