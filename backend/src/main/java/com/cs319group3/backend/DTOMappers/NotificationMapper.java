package com.cs319group3.backend.DTOMappers;

import com.cs319group3.backend.DTOs.NotificationDTO;
import com.cs319group3.backend.Entities.Notification;
import com.cs319group3.backend.Enums.NotificationType;

public class NotificationMapper {
    public static NotificationDTO essentialMapper(Notification notification, String message) {
        NotificationDTO dto = new NotificationDTO();
        dto.setNotificationType(notification.getNotificationType().name());
        dto.setMessage(message);
        dto.setDate(notification.getSentDateTime().toString());

        if (notification.getRequest() != null){
            dto.setRequestId(notification.getRequest().getRequestId());
            dto.setRequestType(notification.getRequest().getClass().getSimpleName());
        }

        return dto;
    }
}
