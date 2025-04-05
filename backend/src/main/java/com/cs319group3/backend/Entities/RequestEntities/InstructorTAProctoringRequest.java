package com.cs319group3.backend.Entities.RequestEntities;


import com.cs319group3.backend.Entities.ClassProctoring;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "instructor_ta_proctoring_request")
public class InstructorTAProctoringRequest extends Request{

    @ManyToOne
    @JoinColumn(name = "class_proctoring_id")
    private ClassProctoring classProctoring;
}
