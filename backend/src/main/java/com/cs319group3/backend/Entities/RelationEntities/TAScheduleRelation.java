package com.cs319group3.backend.Entities.RelationEntities;

import com.cs319group3.backend.CompositeIDs.TAScheduleKey;
import com.cs319group3.backend.Entities.TimeInterval;
import com.cs319group3.backend.Entities.UserEntities.TA;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ta_schedule_relation")
public class TAScheduleRelation{

    @EmbeddedId
    private TAScheduleKey id;

    @ManyToOne
    @MapsId("TAId")
    private TA TA;

    @ManyToOne
    @MapsId("timeIntervalId")
    private TimeInterval timeInterval;

}
