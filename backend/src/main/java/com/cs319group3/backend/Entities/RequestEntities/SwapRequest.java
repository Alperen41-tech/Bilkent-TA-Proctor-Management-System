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
@Table(name = "swap_request")
public class SwapRequest extends Request{


    @ManyToOne
    @JoinColumn(name  = "class_proctoring_id")
    private ClassProctoring classProctoring;
}



