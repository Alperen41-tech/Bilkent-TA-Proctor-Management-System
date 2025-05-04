package com.cs319group3.backend.Repositories;

import com.cs319group3.backend.Entities.RelationEntities.ProctoringApplicationTARelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProctoringApplicationTARelationRepo extends JpaRepository<ProctoringApplicationTARelation, Integer> {


    Optional<ProctoringApplicationTARelation> findByTA_UserIdAndProctoringApplication_ApplicationId(int taUserId, int proctoringApplicationId);

    List<ProctoringApplicationTARelation> findByProctoringApplication_ApplicationIdAndIsApprovedBySecretaryFalse(int proctoringApplicationId);

    List<ProctoringApplicationTARelation> findByProctoringApplication_ApplicationId(int proctoringApplicationId);

}
