package com.cs319group3.backend.Repositories;


import com.cs319group3.backend.Entities.ClassProctoring;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassProctoringRepo extends JpaRepository<ClassProctoring, Integer> {

    List<ClassProctoring> findByCourse_Department_DepartmentId(int departmentId);
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
}
