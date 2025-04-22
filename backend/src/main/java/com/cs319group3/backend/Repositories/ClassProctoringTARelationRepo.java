package com.cs319group3.backend.Repositories;


import com.cs319group3.backend.CompositeIDs.ClassProctoringTAKey;
import com.cs319group3.backend.Entities.RelationEntities.ClassProctoringTARelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassProctoringTARelationRepo extends JpaRepository<ClassProctoringTARelation, ClassProctoringTAKey> {

    List<ClassProctoringTARelation> findById_TAId(Integer taId);
    Optional<ClassProctoringTARelation> findById_ClassProctoringIdAndId_TAId(Integer classProctoringId, Integer taId);
    List<ClassProctoringTARelation> findByClassProctoring_ClassProctoringIdIn(List<Integer> ids);
}
