package com.cs319group3.backend.Repositories;

import com.cs319group3.backend.CompositeIDs.OfferedCourseScheduleKey;
import com.cs319group3.backend.Entities.RelationEntities.OfferedCourseScheduleRelation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferedCourseScheduleRelationRepo extends JpaRepository<OfferedCourseScheduleRelation, OfferedCourseScheduleKey> {
}
