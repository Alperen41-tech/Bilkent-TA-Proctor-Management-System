package com.cs319group3.backend.Services.ServiceImpls;

import com.cs319group3.backend.DTOs.ClassProctoringDTO;
import com.cs319group3.backend.Services.ClassProctoringService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ClassProctoringServiceImpl implements ClassProctoringService {
    @Override
    public List<ClassProctoringDTO> getClassProctoringList() {
        return List.of();
    }
}
