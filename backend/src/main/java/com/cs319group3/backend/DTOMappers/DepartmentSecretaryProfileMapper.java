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
    public static DepartmentSecretary toEntity(DepartmentSecretaryProfileDTO dspDTO){
        DepartmentSecretary departmentSecretary = new DepartmentSecretary();
        departmentSecretary.setName(dspDTO.getName());
        departmentSecretary.setSurname(dspDTO.getSurname());
        departmentSecretary.setEmail(dspDTO.getEmail());
        departmentSecretary.setBilkentId(dspDTO.getBilkentId());
        departmentSecretary.setPhoneNumber(dspDTO.getPhoneNumber());
        departmentSecretary.setActive(dspDTO.isActive());
        return departmentSecretary;
    }
}
