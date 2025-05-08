package com.cs319group3.backend.Repositories;

import com.cs319group3.backend.CompositeIDs.CourseStudentKey;
import com.cs319group3.backend.Entities.RelationEntities.CourseStudentRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseStudentRelationRepo extends JpaRepository<CourseStudentRelation, CourseStudentKey> {
}
