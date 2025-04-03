package com.cs319group3.backend.Entities;


import com.cs319group3.backend.Entities.RelationEntities.ClassProctoringTARelation;
import jakarta.persistence.*;
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
@Table(name = "class_proctoring")
public class ClassProctoring {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int classProctoringId;



    @OneToMany(mappedBy = "classProctoring")
    private List<ClassProctoringTARelation> classProctoringTARelations;;





}
