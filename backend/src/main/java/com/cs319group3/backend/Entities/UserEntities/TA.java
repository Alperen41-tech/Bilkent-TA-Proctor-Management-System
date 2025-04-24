package com.cs319group3.backend.Entities.UserEntities;


import com.cs319group3.backend.Entities.Course;
import com.cs319group3.backend.Entities.Department;
import com.cs319group3.backend.Entities.RelationEntities.ClassProctoringTARelation;
import com.cs319group3.backend.Entities.RequestEntities.InstructorTAProctoringRequest;
import com.cs319group3.backend.Entities.RequestEntities.SwapRequest;
import com.cs319group3.backend.Entities.RequestEntities.TAAvailabilityRequest;
import com.cs319group3.backend.Entities.RequestEntities.WorkloadRequest;
import com.cs319group3.backend.Entities.TAType;
import com.cs319group3.backend.Entities.TimeInterval;
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
    private List<WorkloadRequest> workloadRequests;


    @OneToMany(mappedBy = "senderUser")
    private List<TAAvailabilityRequest> TAAvailabilityRequests;

    @OneToMany(mappedBy = "senderUser")
    private List<SwapRequest> swapRequestsSent;

    @OneToMany(mappedBy = "receiverUser")
    private List<SwapRequest> swapRequestsReceived;


    @OneToMany(mappedBy = "receiverUser")
    private List<InstructorTAProctoringRequest> instructorTAProctoringRequests;




}
