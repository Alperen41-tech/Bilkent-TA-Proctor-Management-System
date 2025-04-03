package com.cs319group3.backend.CompositeIDs;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;



@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TAScheduleKey implements Serializable {
    private int TAId;
    private int timeIntervalId;
}
