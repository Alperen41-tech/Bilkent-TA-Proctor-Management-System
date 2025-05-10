package com.cs319group3.backend.Repositories;

import com.cs319group3.backend.Entities.UserEntities.DeansOffice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeansOfficeRepo extends JpaRepository<DeansOffice, Integer> {
    Optional<DeansOffice> findByUserId(int userId);
    @Query("""
    SELECT d
    FROM ClassProctoring cp
    JOIN cp.course c
    JOIN c.department dept
    JOIN dept.faculty f
    JOIN DeansOffice d ON d.faculty = f
    WHERE cp.classProctoringId = :classProctoringId
""")
    Optional<DeansOffice> findDeansOfficeByClassProctoringId(int classProctoringId);
}
