package com.cs319group3.backend.Services.ServiceImpls;

import com.cs319group3.backend.DTOMappers.ClassProctoringTARelationMapper;
import com.cs319group3.backend.DTOMappers.TAProfileMapper;
import com.cs319group3.backend.DTOs.ClassProctoringAndTAsDTO;
import com.cs319group3.backend.DTOs.ClassProctoringTARelationDTO;
import com.cs319group3.backend.DTOs.TAProfileDTO;
import com.cs319group3.backend.Entities.RelationEntities.ClassProctoringTARelation;
import com.cs319group3.backend.Entities.UserEntities.TA;
import com.cs319group3.backend.Repositories.ClassProctoringRepo;
import com.cs319group3.backend.Repositories.ClassProctoringTARelationRepo;
import com.cs319group3.backend.Repositories.TARepo;
import com.cs319group3.backend.Services.ClassProctoringAndTAs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ClassProctoringAndTAsImpl implements ClassProctoringAndTAs {
    @Autowired
    private TARepo taRepo;

    @Autowired
    private ClassProctoringTARelationRepo classProctoringTARelationRepo;

    @Override
    public List<ClassProctoringAndTAsDTO> getDepartmentTAsClassProctorings(int userId) {
        Optional<TA> ta = taRepo.findByUserId(userId);
        if (!ta.isPresent()) {
            throw new RuntimeException("No TA found with user ID " + userId);
        }

        int departmentId = ta.get().getDepartment().getDepartmentId();

        List<ClassProctoringTARelation> relations =
                classProctoringTARelationRepo.findByClassProctoring_Course_Department_DepartmentId(departmentId);

        List<ClassProctoringAndTAsDTO> results = new ArrayList<>();
        Set<Integer> processedProctoringIds = new HashSet<>();

        for (ClassProctoringTARelation relation : relations) {
            int proctoringId = relation.getClassProctoring().getClassProctoringId();

            // skip if already processed
            if (processedProctoringIds.contains(proctoringId)) {
                continue;
            }

            // mark as processed
            processedProctoringIds.add(proctoringId);

            // get all relations (TAs) for this proctoring
            List<ClassProctoringTARelation> otherRelations =
                    classProctoringTARelationRepo.findByClassProctoring_ClassProctoringId(proctoringId);

            // Map the first relation to ClassProctoringTARelationDTO
            ClassProctoringTARelationDTO relationDTO = ClassProctoringTARelationMapper.essentialMapper(relation);

            // Build TA list
            List<TAProfileDTO> taDTOs = new ArrayList<>();
            for (ClassProctoringTARelation other : otherRelations) {
                TA ta1 = other.getTA();
                TAProfileDTO dto = TAProfileMapper.essentialMapper(ta1);
                taDTOs.add(dto);
            }

            // Build final DTO
            ClassProctoringAndTAsDTO dto = new ClassProctoringAndTAsDTO();
            dto.setClassProctoringTARelationDTO(relationDTO);
            dto.setTaProfileDTOList(taDTOs);

            results.add(dto);
        }

        return results;
    }
}
