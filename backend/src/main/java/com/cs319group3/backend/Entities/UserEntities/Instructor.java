package com.cs319group3.backend.Entities.UserEntities;


import com.cs319group3.backend.Entities.Department;
import com.cs319group3.backend.Entities.RelationEntities.CourseInstructorRelation;
import com.cs319group3.backend.Entities.RequestEntities.AuthStaffProctoringRequest;
import com.cs319group3.backend.Entities.RequestEntities.InstructorAdditionalTARequest;
import com.cs319group3.backend.Entities.RequestEntities.TAWorkloadRequest;
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
    private List<TAWorkloadRequest> workloadRequests;


    @OneToMany(mappedBy = "senderUser")
    private List<InstructorAdditionalTARequest> TAFromDeanRequests;

    @OneToMany(mappedBy = "senderUser")
    private List<AuthStaffProctoringRequest> instructorTAProctoringRequests;

}
