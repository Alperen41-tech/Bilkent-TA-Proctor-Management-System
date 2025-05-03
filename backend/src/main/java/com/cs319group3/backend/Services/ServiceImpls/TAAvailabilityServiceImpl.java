package com.cs319group3.backend.Services.ServiceImpls;

import com.cs319group3.backend.DTOs.TimeIntervalDTO;
import com.cs319group3.backend.Entities.ClassProctoring;
import com.cs319group3.backend.Entities.UserEntities.TA;
import com.cs319group3.backend.Services.TAAvailabilityService;
import com.cs319group3.backend.Services.TimeIntervalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TAAvailabilityServiceImpl implements TAAvailabilityService {

    @Autowired
    TimeIntervalService timeIntervalService;

    @Override
    public boolean isTAAvailable(TA ta, ClassProctoring otherCtr){

        /* ta is not available
         * if he has another proctoring in the time interval
         * if he has singed as with leave of absence
         * if he has lecture
         * if he already recevied a swap reeqeust about that */

        LocalDateTime startDateTime = otherCtr.getStartDate(); // your LocalDateTime value
        LocalDateTime endDateTime = otherCtr.getEndDate();   // your LocalDateTime value


        List<TimeIntervalDTO> taSchedule = timeIntervalService.getTATimeIntervalsByHour(startDateTime, endDateTime, ta.getUserId());

        return taSchedule.isEmpty();
    }
}
