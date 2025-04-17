package com.cs319group3.backend.Entities.RelationEntities;

import com.cs319group3.backend.CompositeIDs.ClassProctoringTAKey;
import com.cs319group3.backend.Entities.ClassProctoring;
import com.cs319group3.backend.Entities.UserEntities.TA;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "class_proctoring_ta_relation")
public class ClassProctoringTARelation{

    @EmbeddedId
    private ClassProctoringTAKey id;

    private boolean isPaid;
    private boolean isComplete;
    private boolean isOpenToSwap;

    @ManyToOne
    @MapsId("classProctoringId")
    @JoinColumn(name = "class_proctoring_id")
    private ClassProctoring classProctoring;


    @ManyToOne
    @MapsId("TAId")
    @JoinColumn(name = "ta_id")
    private TA TA;
}
