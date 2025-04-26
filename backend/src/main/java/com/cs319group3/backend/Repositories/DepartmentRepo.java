package com.cs319group3.backend.Repositories;

import com.cs319group3.backend.Entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepo extends JpaRepository<Department, Integer> {
    Optional<Department> findByDepartmentCode(String code);

    @Query("SELECT d.departmentId FROM Department d WHERE d.faculty.facultyId = :facultyId")
    List<Integer> findDepartmentIdsByFacultyId(@Param("facultyId") int facultyId);

}
