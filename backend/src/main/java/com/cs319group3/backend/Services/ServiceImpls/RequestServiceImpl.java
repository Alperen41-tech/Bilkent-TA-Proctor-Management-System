package com.cs319group3.backend.Services.ServiceImpls;

import com.cs319group3.backend.Entities.Notification;
import com.cs319group3.backend.Entities.RequestEntities.Request;
import com.cs319group3.backend.Repositories.NotificationRepo;
import com.cs319group3.backend.Repositories.RequestRepo;
import com.cs319group3.backend.Services.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.cs319group3.backend.Enums.NotificationType.APPROVAL;
import static com.cs319group3.backend.Enums.NotificationType.REQUEST;

@Service
public class RequestServiceImpl implements RequestService {

    @Autowired
    private RequestRepo requestRepo;

    @Autowired
    private NotificationRepo notificationRepo;

    @Override
    public boolean respondToRequest(int requestId, boolean response) {
        Optional<Request> request = requestRepo.findByRequestId(requestId);
        if (request.isEmpty()) {
            return false;
        }
        request.get().setIsApproved(response);
        request.get().setResponseDate(LocalDateTime.now());
        requestRepo.save(request.get());

        Notification notification = new Notification();
        notification.setRequest(request.get());
        notification.setNotificationType(APPROVAL);
        notification.setRead(false);
        notification.setReceiver(request.get().getSenderUser());
        notificationRepo.save(notification);

        return true;
    }
}
