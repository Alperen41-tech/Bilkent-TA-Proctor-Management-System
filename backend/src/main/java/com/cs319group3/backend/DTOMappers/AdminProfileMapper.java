package com.cs319group3.backend.DTOMappers;

import com.cs319group3.backend.DTOs.AdminProfileDTO;
import com.cs319group3.backend.Entities.UserEntities.User;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;

public class AdminProfileMapper {
    public static AdminProfileDTO toDTO(User user){
        AdminProfileDTO dto = new AdminProfileDTO();
        dto.setBilkentId(user.getBilkentId());
        dto.setUserId(user.getUserId());
        dto.setName(user.getName());
        dto.setSurname(user.getSurname());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setActive(user.isActive());
        return dto;
    }
}
