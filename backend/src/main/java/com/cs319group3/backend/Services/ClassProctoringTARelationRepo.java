package com.cs319group3.backend.Services;


import com.cs319group3.backend.Entities.RelationEntities.ClassProctoringTARelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassProctoringTARelationRepo extends JpaRepository<ClassProctoringTARelation, Integer> {

}
