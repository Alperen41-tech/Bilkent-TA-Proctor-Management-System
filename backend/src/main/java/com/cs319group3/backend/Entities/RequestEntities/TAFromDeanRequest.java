package com.cs319group3.backend.Entities.RequestEntities;

import com.cs319group3.backend.Entities.ClassProctoring;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ta_from_dean_request")
public class TAFromDeanRequest extends Request{


    //TODO TACount column should be added to table in db
    private int TACount;

    private boolean isComplete;

    @ManyToOne
    @JoinColumn(name  = "class_proctoring_id")
    private ClassProctoring classProctoring;



}
