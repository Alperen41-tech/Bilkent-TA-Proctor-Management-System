package com.cs319group3.backend.Repositories;

import com.cs319group3.backend.Entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepo extends JpaRepository<Department, Integer> {
    Optional<Department> findByDepartmentCode(String code);

    @Query("SELECT d.departmentId FROM Department d WHERE d.faculty.facultyId = :facultyId")
    List<Integer> findDepartmentIdsByFacultyId(@Param("facultyId") int facultyId);

    List<Department> findByFacultyFacultyId(int facultyId);

    @Query("SELECT d FROM Department d WHERE d.faculty.facultyId = :facultyId AND d.departmentId <> :departmentId")
    List<Department> findByFacultyIdAndDepartmentIdNot(int facultyId,int departmentId);

    @Query("""
    SELECT dc.departmentCode FROM Department dc
""")
    List<String> findAllDepartmentCodes();

}
