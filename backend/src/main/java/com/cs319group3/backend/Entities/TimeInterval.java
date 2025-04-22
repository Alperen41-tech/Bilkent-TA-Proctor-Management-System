package com.cs319group3.backend.Entities;


import com.cs319group3.backend.Entities.RelationEntities.OfferedCourseScheduleRelation;
import com.cs319group3.backend.Entities.RelationEntities.TAScheduleRelation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "time_interval")
public class TimeInterval {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int timeIntervalId;

    private String day;

    private Time startTime;

    private Time endTime;

    @OneToMany(mappedBy = "timeInterval")
    private List<OfferedCourseScheduleRelation> offeredCourseScheduleRelations;

    @OneToMany(mappedBy = "timeInterval")
    private List<TAScheduleRelation> TAScheduleRelations;
}
