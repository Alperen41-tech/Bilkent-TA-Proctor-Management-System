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

    @ManyToOne
    @MapsId("classProctoringId")
    private ClassProctoring classProctoring;

    public String getClassroom(){
        return id != null ? id.getClassroom() : null;
    }

    public void setClassroom(String classroom){

        if (id == null) {
            id = new ClassProctoringClassroomKey();
        }
        id.setClassroom(classroom);
    }

}
