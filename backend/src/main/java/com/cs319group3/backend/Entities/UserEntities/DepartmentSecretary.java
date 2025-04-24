package com.cs319group3.backend.Entities.UserEntities;


import com.cs319group3.backend.Entities.Department;
import com.cs319group3.backend.Entities.RequestEntities.TALeaveRequest;
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
@Table(name = "department_secretary")
public class DepartmentSecretary extends User{

    @OneToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToMany(mappedBy = "receiverUser")
    private List<TALeaveRequest> availabilityRequests;


}
