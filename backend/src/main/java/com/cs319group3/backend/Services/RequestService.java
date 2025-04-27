package com.cs319group3.backend.Services;

import com.cs319group3.backend.DTOs.RequestDTO;

import java.util.List;

public interface RequestService {
    public boolean respondToRequest(int requestId, boolean response);
    public List<RequestDTO> getRequestsByReceiverUser(int userId);
    public List<RequestDTO> getRequestsBySenderUser(int userId);
}
