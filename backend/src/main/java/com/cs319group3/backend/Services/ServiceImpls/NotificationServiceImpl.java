package com.cs319group3.backend.Services.ServiceImpls;

import com.cs319group3.backend.DTOMappers.NotificationMapper;
import com.cs319group3.backend.DTOs.NotificationDTO;
import com.cs319group3.backend.Entities.Notification;
import com.cs319group3.backend.Repositories.NotificationRepo;
import com.cs319group3.backend.Services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepo notificationRepo;

    @Override
    public List<NotificationDTO> getNotifications(int userId) {
        List<Notification> notifications = notificationRepo.findByReceiver_UserId(userId);
        List<NotificationDTO> notificationDTOs = new ArrayList<>();
        for (Notification notification : notifications) {
            String message = "test"; //to be implemented according to frontend
            NotificationDTO dto = NotificationMapper.essentialMapper(notification, message);
            notificationDTOs.add(dto);
        }
        return notificationDTOs;
    }

    @Override
    public boolean setNotificationsRead(int userId) {
        List<Notification> notifications = notificationRepo.findByReceiver_UserId(userId);
        for(Notification notification : notifications) {
            if (!notification.isRead()) {
                notification.setRead(true);
                notificationRepo.save(notification);
            }
        }
        return true;
    }

    @Override
    public int getUnreadNotificationCount(int userId) {
        List<Notification> notifications = notificationRepo.findByReceiver_UserId(userId);
        int count = 0;
        for (Notification notification : notifications) {
            if (!notification.isRead()) {
                count++;
            }
        }
        return count;
    }
}