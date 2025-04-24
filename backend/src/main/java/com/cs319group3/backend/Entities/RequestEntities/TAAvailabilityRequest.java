package com.cs319group3.backend.Entities.RequestEntities;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ta_availability_request")
public class TAAvailabilityRequest extends Request{

    private boolean isUrgent;

    private LocalDateTime unavailabilityStartDate;
    private LocalDateTime unavailabilityEndDate;

}
