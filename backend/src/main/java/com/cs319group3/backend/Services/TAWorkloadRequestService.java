package com.cs319group3.backend.Services;

import com.cs319group3.backend.DTOs.TAWorkloadRequestDTO;

import java.util.List;

public interface TAWorkloadRequestService {
    public boolean createTAWorkloadRequest(TAWorkloadRequestDTO dto, int taId);
    public List<TAWorkloadRequestDTO> getTAWorkloadRequests(int taId);
    public int getTotalWorkload(int taId);
}
