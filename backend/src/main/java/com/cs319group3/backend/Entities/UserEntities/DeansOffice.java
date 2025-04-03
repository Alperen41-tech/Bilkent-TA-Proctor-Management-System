package com.cs319group3.backend.Entities.UserEntities;


import com.cs319group3.backend.Entities.Faculty;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@Table(name = "deans_office")
public class DeansOffice extends User {

    @OneToOne
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;

}
