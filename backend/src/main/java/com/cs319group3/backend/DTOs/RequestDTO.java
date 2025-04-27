package com.cs319group3.backend.DTOs;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestDTO {
    int requestId;
    int senderId;
    int receiverId;
    String sentDateTime;
    boolean isApproved;
    String responseDateTime;
    String description;
}
