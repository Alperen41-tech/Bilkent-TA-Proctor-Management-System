package com.cs319group3.backend.Repositories;


import com.cs319group3.backend.CompositeIDs.ClassProctoringTAKey;
import com.cs319group3.backend.Entities.Course;
import com.cs319group3.backend.Entities.RelationEntities.ClassProctoringTARelation;
import com.cs319group3.backend.Entities.UserEntities.TA;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClassProctoringTARelationRepo extends JpaRepository<ClassProctoringTARelation, ClassProctoringTAKey> {

    List<ClassProctoringTARelation> findById_TAId(Integer taId);
    Optional<ClassProctoringTARelation> findById_ClassProctoringIdAndId_TAId(Integer classProctoringId, Integer taId);
    List<ClassProctoringTARelation> findByClassProctoring_ClassProctoringIdIn(List<Integer> ids);
    List<ClassProctoringTARelation> findByClassProctoring_Course_Department_DepartmentId(Integer departmentId);
    List<ClassProctoringTARelation> findByClassProctoring_Course_Department_DepartmentCode(String departmentCode);
    List<ClassProctoringTARelation> findByClassProctoring_ClassProctoringId(Integer id);
    List<ClassProctoringTARelation> findByClassProctoring_Course(Course classProctoringCourse);
    List<ClassProctoringTARelation> findByClassProctoring_Course_Department_DepartmentIdAndTA_UserId(int departmentId, Integer taUserId);

    @Query("""
    SELECT COUNT(ctr)
    FROM ClassProctoringTARelation ctr
    WHERE ctr.classProctoring.classProctoringId = :classProctoringId
""")
    int countByClassProctoringId(int classProctoringId);

    @Query("SELECT cptr FROM ClassProctoringTARelation cptr " +
            "JOIN cptr.classProctoring cp " +
            "WHERE cp.creator.userId = :creatorId")
    List<ClassProctoringTARelation> findByCreator_UserId(int creatorId);

    @Query("SELECT cptr.TA FROM ClassProctoringTARelation cptr " +
            "WHERE cptr.classProctoring.classProctoringId = :classProctoringId")
    List<TA> findTAsByClassProctoringId(int classProctoringId);

    @Query("""
    SELECT COUNT(c)
    FROM ClassProctoringTARelation c
    WHERE c.classProctoring.classProctoringId = :classProctoringId
""")
    int countAssignedTAs(int classProctoringId);


    List<ClassProctoringTARelation> findByTA_UserIdAndClassProctoring_StartDateLessThanEqualAndClassProctoring_EndDateGreaterThan(int taId, LocalDateTime start, LocalDateTime end);

}
