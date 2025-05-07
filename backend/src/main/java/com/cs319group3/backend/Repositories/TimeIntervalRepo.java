package com.cs319group3.backend.Repositories;

import com.cs319group3.backend.Entities.TimeInterval;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Time;
import java.time.LocalTime;

public interface TimeIntervalRepo extends JpaRepository<TimeInterval, Integer> {

    TimeInterval findByStartTimeAndDay(LocalTime startTime, String day);

}
