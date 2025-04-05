package com.cs319group3.backend.Entities.UserEntities;


import com.cs319group3.backend.Entities.Department;
import com.cs319group3.backend.Entities.RelationEntities.CourseInstructorRelation;
import com.cs319group3.backend.Entities.RequestEntities.InstructorTAProctoringRequest;
import com.cs319group3.backend.Entities.RequestEntities.TAFromDeanRequest;
import com.cs319group3.backend.Entities.RequestEntities.WorkloadRequest;
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
@Table(name = "instructor")
public class Instructor extends User{






    @OneToMany(mappedBy = "instructor")
    private List<CourseInstructorRelation> courseInstructorRelations;


    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;


    @OneToMany(mappedBy = "receiverUser")
    private List<WorkloadRequest> workloadRequests;


    @OneToMany(mappedBy = "senderUser")
    private List<TAFromDeanRequest> TAFromDeanRequests;

    @OneToMany(mappedBy = "senderUser")
    private List<InstructorTAProctoringRequest> instructorTAProctoringRequests;

}
