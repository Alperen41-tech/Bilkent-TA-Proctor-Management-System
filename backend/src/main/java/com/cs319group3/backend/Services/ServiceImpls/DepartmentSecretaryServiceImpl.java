package com.cs319group3.backend.Services.ServiceImpls;

import com.cs319group3.backend.DTOMappers.DepartmentSecretaryProfileMapper;
import com.cs319group3.backend.DTOs.DepartmentSecretaryProfileDTO;
import com.cs319group3.backend.Entities.UserEntities.DepartmentSecretary;
import com.cs319group3.backend.Repositories.DepartmentSecretaryRepo;
import com.cs319group3.backend.Services.DepartmentSecretaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DepartmentSecretaryServiceImpl implements DepartmentSecretaryService {

    @Autowired
    private DepartmentSecretaryRepo departmentSecretaryRepo;

    @Override
    public DepartmentSecretaryProfileDTO getDepartmentSecretaryProfileById(int id){
        Optional<DepartmentSecretary> departmentSecretary = departmentSecretaryRepo.findByUserId(id);

        if(departmentSecretary.isEmpty()){
            throw new RuntimeException("Department Secretary with ID " + id + " not found.");
        }

        return DepartmentSecretaryProfileMapper.essentialMapper(departmentSecretary.get());
    }
}
