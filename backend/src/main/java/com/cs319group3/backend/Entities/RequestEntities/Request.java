package com.cs319group3.backend.Entities.RequestEntities;


import com.cs319group3.backend.Entities.UserEntities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "request")
public abstract class Request{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int requestId;


    //TODO buraya bir daha bak
    private LocalDateTime sentDate;
    private boolean isApproved; //null if not responded yet
    private LocalDateTime responseDate;
    private String description;


    @ManyToOne
    @JoinColumn(name = "sender_user_id")
    protected User senderUser;

    @ManyToOne
    @JoinColumn(name = "receiver_user_id")
    protected User receiverUser;

}

