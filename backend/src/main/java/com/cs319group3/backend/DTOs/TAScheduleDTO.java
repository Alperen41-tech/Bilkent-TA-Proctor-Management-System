package com.cs319group3.backend.DTOs;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TAScheduleDTO {
    String eventName;
    String dayOfWeek;
    String startTime;
    String endTime;
}
