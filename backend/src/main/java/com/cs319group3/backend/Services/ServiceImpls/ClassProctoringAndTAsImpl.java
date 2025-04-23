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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClassProctoringAndTAsImpl implements ClassProctoringAndTAs {
    @Autowired
    private TARepo taRepo;

    @Autowired
    private ClassProctoringTARelationRepo classProctoringTARelationRepo;

    @Override
    public List<ClassProctoringAndTAsDTO> getDepartmentTAsClassProctorings(int userId){
        // Step 1: Find TA by user ID
        Optional<TA> ta = taRepo.findByUserId(userId);
        if (!ta.isPresent()) {
            throw new RuntimeException("No TA found with user ID " + userId);
        }

        int departmentId = ta.get().getDepartment().getDepartmentId();

        // Step 2: Find all relations in this department (not just for this TA)
        List<ClassProctoringTARelation> relations =
                classProctoringTARelationRepo.findByClassProctoring_Course_Department_DepartmentId(departmentId);

        // Step 3: Convert to DTOs
        List<ClassProctoringAndTAsDTO> results = new ArrayList<>();
        for (ClassProctoringTARelation relation : relations) {
            ClassProctoringTARelationDTO relationDTO = ClassProctoringTARelationMapper.essentialMapper(relation);

            // Step 2: Get all other TAs in the same class proctoring
            List<ClassProctoringTARelation> otherRelations =
                    classProctoringTARelationRepo.findByClassProctoring_ClassProctoringId(
                            relation.getClassProctoring().getClassProctoringId()
                    );

            List<TAProfileDTO> taDTOs = new ArrayList<>();

            for (ClassProctoringTARelation otherRelation : otherRelations) {
                TA ta1 = otherRelation.getTA(); // get the TA from the relation
                TAProfileDTO dto = TAProfileMapper.essentialMapper(ta1); // convert TA to TAProfileDTO
                taDTOs.add(dto); // add to the list
            }

            ClassProctoringAndTAsDTO dto = new ClassProctoringAndTAsDTO();
            dto.setClassProctoringTARelationDTO(relationDTO);
            dto.setTaProfileDTOList(taDTOs);

            results.add(dto);
        }

        return results;
    }
}
