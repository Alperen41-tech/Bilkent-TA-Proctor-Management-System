package com.cs319group3.backend.Repositories;

import com.cs319group3.backend.CompositeIDs.CourseInstructorKey;
import com.cs319group3.backend.Entities.RelationEntities.CourseInstructorRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseInstructorRelationRepo extends JpaRepository<CourseInstructorRelation, CourseInstructorKey> {
}
