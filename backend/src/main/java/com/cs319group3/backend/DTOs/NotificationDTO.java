package com.cs319group3.backend.DTOs;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {
    private String requestType;
    private int requestId;
    private String notificationType;
    private String message;
    private String date;
}
