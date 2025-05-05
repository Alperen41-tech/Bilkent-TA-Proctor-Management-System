package com.cs319group3.backend.Repositories;

import com.cs319group3.backend.Entities.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LogRepo extends JpaRepository<Log, Long> {
    List<Log> getLogsByLogDateBetween(LocalDateTime logDateAfter, LocalDateTime logDateBefore);
}
