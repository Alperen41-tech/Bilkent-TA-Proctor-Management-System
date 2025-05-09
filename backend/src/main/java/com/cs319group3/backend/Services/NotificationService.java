package com.cs319group3.backend.Services;

import com.cs319group3.backend.DTOs.NotificationDTO;
import com.cs319group3.backend.Entities.Notification;
import com.cs319group3.backend.Entities.RequestEntities.Request;
import com.cs319group3.backend.Entities.UserEntities.User;
import com.cs319group3.backend.Enums.NotificationType;

import java.util.List;

public interface NotificationService {

    /**
     * Retrieves a list of notifications for the given user ID.
     *
     * @param userId the user's ID
     * @return a list of NotificationDTO objects
     */
    List<NotificationDTO> getNotifications(int userId);

    /**
     * Marks all notifications as read for the given user ID.
     *
     * @param userId the user's ID
     * @return true if successful, false otherwise
     */
    boolean setNotificationsRead(int userId);

    /**
     * Returns the count of unread notifications for the user.
     *
     * @param userId the user's ID
     * @return number of unread notifications
     */
    int getUnreadNotificationCount(int userId);

    /**
     * Creates a notification based on a request and type.
     *
     * @param request the associated request
     * @param type    the type of notification
     * @return the created Notification entity
     */
    Notification createNotification(Request request, NotificationType type);

    /**
     * Creates a notification with a custom description based on a request and type.
     *
     * @param request     the associated request
     * @param type        the type of notification
     * @param description the custom message
     * @return the created Notification entity
     */
    Notification createNotification(Request request, NotificationType type, String description);

    /**
     * Creates a standalone notification not linked to a request.
     *
     * @param type        the type of notification
     * @param user        the target user
     * @param description the custom message
     * @return the created Notification entity
     */
    Notification createNotificationWithoutRequest(NotificationType type, User user, String description);
}