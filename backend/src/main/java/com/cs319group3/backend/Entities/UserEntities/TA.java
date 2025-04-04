package com.cs319group3.backend.Entities.UserEntities;


import com.cs319group3.backend.Entities.RelationEntities.ClassProctoringTARelation;
import com.cs319group3.backend.Entities.RelationEntities.TAScheduleRelation;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ta")
public class TA extends User{





    @OneToMany(mappedBy = "TA")
    private List<TAScheduleRelation> TAScheduleRelations;

    @OneToMany(mappedBy = "TA")
    private List<ClassProctoringTARelation> classProctoringTARelations;


}
