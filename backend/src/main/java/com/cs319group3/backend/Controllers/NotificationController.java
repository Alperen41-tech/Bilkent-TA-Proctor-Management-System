package com.cs319group3.backend.Controllers;

import com.cs319group3.backend.Components.CurrentUserUtil;
import com.cs319group3.backend.DTOs.NotificationDTO;
import com.cs319group3.backend.Services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("notification")
@ComponentScan(basePackages = {"com.cs319group3.backend.Controllers"})
@CrossOrigin(origins = "http://localhost:3000")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private CurrentUserUtil currentUserUtil;

    @GetMapping("get")
    public List<NotificationDTO> getNotifications() {
        int userId = currentUserUtil.getCurrentUserId();
        return notificationService.getNotifications(userId);
    }

    @PutMapping("setRead")
    public boolean setNotificationsRead() {
        int userId = currentUserUtil.getCurrentUserId();
        return notificationService.setNotificationsRead(userId);
    }

    @GetMapping("getUnreadCount")
    public int getUnreadNotificationCount() {
        int userId = currentUserUtil.getCurrentUserId();
        return notificationService.getUnreadNotificationCount(userId);
    }
}
