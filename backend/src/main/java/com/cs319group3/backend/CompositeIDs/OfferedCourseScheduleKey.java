package com.cs319group3.backend.CompositeIDs;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class OfferedCourseScheduleKey implements Serializable {

    private int offeredCourseId;
    private int timeIntervalId;

}
