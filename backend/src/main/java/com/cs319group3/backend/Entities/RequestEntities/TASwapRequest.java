package com.cs319group3.backend.Entities.RequestEntities;


import com.cs319group3.backend.Entities.ClassProctoring;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ta_swap_request")
public class TASwapRequest extends Request{


    @ManyToOne
    @JoinColumn(name  = "class_proctoring_id")
    private ClassProctoring classProctoring;
}



