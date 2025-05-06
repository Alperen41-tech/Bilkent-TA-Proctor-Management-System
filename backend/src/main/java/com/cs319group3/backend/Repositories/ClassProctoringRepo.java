package com.cs319group3.backend.Repositories;


import com.cs319group3.backend.Entities.ClassProctoring;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassProctoringRepo extends JpaRepository<ClassProctoring, Integer> {

    List<ClassProctoring> findByCourse_Department_DepartmentId(int departmentId);
    List<ClassProctoring> findByCourse_Department_DepartmentIdAndIsCompleteFalse(int departmentId);
    List<ClassProctoring> findByCourse_CourseId(int courseCourseId);

    @Query("""
    SELECT cp.course.department.departmentId
    FROM ClassProctoring cp
    WHERE cp.classProctoringId = :classProctoringId
""")
    Integer findDepartmentIdByClassProctoringId(int classProctoringId);

    @Query("""
    SELECT cp.TACount
    FROM ClassProctoring cp
    WHERE cp.classProctoringId = :classProctoringId
""")
    Integer findCountByClassProctoringId(int classProctoringId);

    List<ClassProctoring> findByCreatorUserId(int creatorId);

    @Query("""
    SELECT cp
    FROM ClassProctoring cp
    JOIN cp.course c
    JOIN OfferedCourse oc ON oc.course = c
    JOIN CourseInstructorRelation cir ON cir.course = oc
    WHERE cir.instructor.userId = :instructorId
""")
    List<ClassProctoring> findClassProctoringsByInstructorId(int instructorId);

    @Query("""
    SELECT cp
    FROM ClassProctoring cp
    JOIN cp.course c
    JOIN c.department d
    WHERE d.departmentCode = :deptCode
""")
    List<ClassProctoring> findAllByDepartmentCode(@Param("deptCode") String deptCode);

    @Query("""
    SELECT rel.classProctoring
    FROM ClassProctoringTARelation rel
    WHERE rel.TA.userId = :taId
""")
    List<ClassProctoring> findProctoringsByTAId(@Param("taId") int taId);

    Optional<ClassProctoring> findByClassProctoringId(int proctoringId);
}
