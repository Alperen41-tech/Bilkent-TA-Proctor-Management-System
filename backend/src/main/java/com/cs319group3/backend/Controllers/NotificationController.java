package com.cs319group3.backend.Controllers;

import com.cs319group3.backend.Components.CurrentUserUtil;
import com.cs319group3.backend.DTOs.NotificationDTO;
import com.cs319group3.backend.Services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsible for handling notification retrieval and update operations
 * for the currently logged-in user.
 */
@RestController
@RequestMapping("notification")
@ComponentScan(basePackages = {"com.cs319group3.backend.Controllers"})
@CrossOrigin(origins = "http://localhost:3000")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private CurrentUserUtil currentUserUtil;

    /**
     * Retrieves all notifications for the currently logged-in user.
     *
     * @return list of NotificationDTOs
     */
    @GetMapping("get")
    public List<NotificationDTO> getNotifications() {
        int userId = currentUserUtil.getCurrentUserId();
        return notificationService.getNotifications(userId);
    }

    /**
     * Marks all unread notifications as read for the current user.
     *
     * @return true if update was successful
     */
    @PutMapping("setRead")
    public boolean setNotificationsRead() {
        int userId = currentUserUtil.getCurrentUserId();
        return notificationService.setNotificationsRead(userId);
    }

    /**
     * Retrieves the count of unread notifications for the current user.
     *
     * @return number of unread notifications
     */
    @GetMapping("getUnreadCount")
    public int getUnreadNotificationCount() {
        int userId = currentUserUtil.getCurrentUserId();
        return notificationService.getUnreadNotificationCount(userId);
    }
}