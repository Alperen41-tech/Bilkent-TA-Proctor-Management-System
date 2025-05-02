package com.cs319group3.backend.Services;

import com.cs319group3.backend.DTOs.ProctoringApplicationDTO;
import com.cs319group3.backend.DTOs.RequestDTOs.RequestDTO;
import com.cs319group3.backend.Entities.RequestEntities.Request;

import java.util.List;

public interface RequestService {
    public boolean respondToRequest(int requestId, boolean response);
    public boolean deleteRequest(int requestId);
    public List<RequestDTO> getRequestsByReceiverUser(int userId);
    public List<RequestDTO> getRequestsBySenderUser(int userId);
    public Request createProctoringApplicationRequest(ProctoringApplicationDTO proctoringApplicationDTO, int deansOfficeId);
}
