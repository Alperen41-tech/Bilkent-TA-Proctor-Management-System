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
public class ClassProctoringTAKey implements Serializable {

    private int classProctoringId;
    private int TAId;
}
