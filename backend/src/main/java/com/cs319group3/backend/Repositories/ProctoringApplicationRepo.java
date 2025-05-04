package com.cs319group3.backend.Repositories;

import com.cs319group3.backend.Entities.ProctoringApplication;
import com.cs319group3.backend.Enums.ProctoringApplicationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProctoringApplicationRepo extends JpaRepository<ProctoringApplication, Integer> {
    @Query("""
    SELECT pa FROM ProctoringApplication pa
    JOIN pa.classProctoring cp
    JOIN cp.course c
    JOIN c.department d
    JOIN DeansOffice do ON d.faculty.facultyId = do.faculty.facultyId
    WHERE do.userId = :deansOfficeId
    """)
    List<ProctoringApplication> findByDeansOfficeId(int deansOfficeId);

    List<ProctoringApplication> findByVisibleDepartment_DepartmentId(int departmentId);
    List<ProctoringApplication> findByVisibleDepartment_DepartmentIdNotAndApplicationType(int departmentId, ProctoringApplicationType applicationType);
}
