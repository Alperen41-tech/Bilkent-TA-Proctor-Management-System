package com.cs319group3.backend.Repositories;


import com.cs319group3.backend.Entities.ClassProctoring;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassProctoringRepo extends JpaRepository<ClassProctoring, Integer> {


}
