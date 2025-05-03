package com.cs319group3.backend.Entities;


import com.cs319group3.backend.Entities.RequestEntities.Request;
import com.cs319group3.backend.Entities.UserEntities.User;
import com.cs319group3.backend.Enums.NotificationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int notification_id;


    @ManyToOne
    @JoinColumn(name = "request_id")
    private Request request;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    private boolean isRead;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    String description;
}
