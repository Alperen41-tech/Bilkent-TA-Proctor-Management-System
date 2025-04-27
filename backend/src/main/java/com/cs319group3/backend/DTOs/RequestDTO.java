package com.cs319group3.backend.DTOs;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestDTO {
    int requestId;
    String requestType;
    int senderId;
    int receiverId;
    String sentDateTime;
    Boolean isApproved;
    String responseDateTime;
    String description;
}
