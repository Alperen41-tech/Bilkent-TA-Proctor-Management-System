package com.cs319group3.backend.Entities.RelationEntities;


import com.cs319group3.backend.Entities.ProctoringApplication;
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
@Table(name = "proctoring_application_ta_relation")
public class ProctoringApplicationTARelation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int proctoringApplicationTaRelationId;

    @ManyToOne
    @JoinColumn(name = "ta_id")
    private TA TA;

    @ManyToOne
    @JoinColumn(name = "proctoring_application_id")
    private ProctoringApplication proctoringApplication;

    private Boolean isApprovedBySecretary;

}
