package com.cs319group3.backend.Services;

import com.cs319group3.backend.DTOs.RequestDTOs.RequestDTO;

import java.util.List;

public interface TAWorkloadRequestService {
    public boolean createTAWorkloadRequest(RequestDTO dto, int taId);
    public List<RequestDTO> getTAWorkloadRequestsByTA(int taId);
    public int getTotalWorkload(int taId);
    public List<RequestDTO> getTAWorkloadRequestsByInstructor(int instructorId);
}
