package com.cs319group3.backend.Services;

import com.cs319group3.backend.DTOs.NotificationDTO;
import com.cs319group3.backend.Entities.Notification;
import com.cs319group3.backend.Entities.RequestEntities.Request;
import com.cs319group3.backend.Enums.NotificationType;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface NotificationService {
    public List<NotificationDTO> getNotifications(int userId);
    public boolean setNotificationsRead(int userId);
    public int getUnreadNotificationCount(int userId);
    public Notification createNotification(Request request, NotificationType type);
}
