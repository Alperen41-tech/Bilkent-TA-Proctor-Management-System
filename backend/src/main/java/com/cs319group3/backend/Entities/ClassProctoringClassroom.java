package com.cs319group3.backend.Entities;


import com.cs319group3.backend.CompositeIDs.ClassProctoringClassroomKey;
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
@Table(name = "class_proctoring_classroom")
public class ClassProctoringClassroom {

    @EmbeddedId
    private ClassProctoringClassroomKey id;

    private String classroom;

    @ManyToOne
    @JoinColumn(name = "class_proctoring_id")
    private ClassProctoring classProctoring;


}
