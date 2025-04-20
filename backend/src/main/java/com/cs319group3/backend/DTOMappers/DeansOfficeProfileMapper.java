package com.cs319group3.backend.DTOMappers;

import com.cs319group3.backend.DTOs.DeansOfficeProfileDTO;
import com.cs319group3.backend.Entities.UserEntities.DeansOffice;

public class DeansOfficeProfileMapper {
    public static DeansOfficeProfileDTO essentialMapper(DeansOffice deansOffice) {
        DeansOfficeProfileDTO dto = new DeansOfficeProfileDTO();
        dto.setName(deansOffice.getName());
        dto.setEmail(deansOffice.getEmail());
        dto.setSurname(deansOffice.getSurname());
        dto.setBilkentId(deansOffice.getBilkentId());
        dto.setRole("Deans Office");
        dto.setFaculty(deansOffice.getFaculty().getFacultyName());
        return dto;
    }
}
