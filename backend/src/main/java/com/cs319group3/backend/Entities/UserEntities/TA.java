package com.cs319group3.backend.Entities.UserEntities;


import com.cs319group3.backend.Entities.Course;
import com.cs319group3.backend.Entities.Department;
import com.cs319group3.backend.Entities.RelationEntities.ClassProctoringTARelation;
import com.cs319group3.backend.Entities.RequestEntities.AuthStaffProctoringRequest;
import com.cs319group3.backend.Entities.RequestEntities.TASwapRequest;
import com.cs319group3.backend.Entities.RequestEntities.TALeaveRequest;
import com.cs319group3.backend.Entities.RequestEntities.TAWorkloadRequest;
import com.cs319group3.backend.Entities.TAType;
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
@Table(name = "ta")
public class TA extends User{


    @Column(name = "class")
    private int classYear;

    @ManyToOne
    @JoinColumn(name = "ta_type_id")
    private TAType taType;

    @OneToMany(mappedBy = "TA")
    private List<ClassProctoringTARelation> classProctoringTARelations;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course assignedCourse;


    @OneToMany(mappedBy = "senderUser")
    private List<TAWorkloadRequest> workloadRequests;


    @OneToMany(mappedBy = "senderUser")
    private List<TALeaveRequest> TAAvailabilityRequests;

    @OneToMany(mappedBy = "senderUser")
    private List<TASwapRequest> swapRequestsSent;

    @OneToMany(mappedBy = "receiverUser")
    private List<TASwapRequest> swapRequestsReceived;


    @OneToMany(mappedBy = "receiverUser")
    private List<AuthStaffProctoringRequest> instructorTAProctoringRequests;




}
