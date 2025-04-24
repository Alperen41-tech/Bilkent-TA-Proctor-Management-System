package com.cs319group3.backend.Entities.UserEntities;


import com.cs319group3.backend.Entities.Faculty;
import com.cs319group3.backend.Entities.RequestEntities.InstructorAdditionalTARequest;
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
@Table(name = "deans_office")
public class DeansOffice extends User {

    @OneToOne
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;

    @OneToMany(mappedBy = "receiverUser")
    private List<InstructorAdditionalTARequest> TARequestsFromInstructors;

}
