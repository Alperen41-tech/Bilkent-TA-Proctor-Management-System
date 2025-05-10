package com.cs319group3.backend.Repositories;


import com.cs319group3.backend.Entities.UserEntities.TA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TARepo extends JpaRepository<TA, Integer> {
    Optional<TA> findByUserId(int userId);

    @Query("""
    SELECT t FROM TA t
    WHERE t.department.departmentCode = :departmentCode
      AND t.userId NOT IN (
        SELECT ctr.TA.userId FROM ClassProctoringTARelation ctr
        WHERE ctr.classProctoring.classProctoringId = :classProctoringId
      )
""")
    List<TA> findAvailableTAsByDepartment(String departmentCode, int classProctoringId);

    @Query("""
    SELECT t FROM TA t
    WHERE t.department.faculty.facultyId = :facultyId
      AND t.userId NOT IN (
        SELECT ctr.TA.userId FROM ClassProctoringTARelation ctr
        WHERE ctr.classProctoring.classProctoringId = :classProctoringId
      )
""")
    List<TA> findAvailableTAsByFaculty(int facultyId, int classProctoringId);

    @Query("""
    SELECT t.department.departmentId
    FROM TA t
    WHERE t.userId = :userId
""")
    Integer findDepartmentIdByUserId(@Param("userId") int userId);

    Optional<TA> findByBilkentId(String bilkentId);

    Optional<TA> findByEmail(String email);

}
