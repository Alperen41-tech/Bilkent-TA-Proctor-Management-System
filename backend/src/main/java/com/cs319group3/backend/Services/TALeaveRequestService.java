package com.cs319group3.backend.Services;

import com.cs319group3.backend.DTOs.RequestDTOs.RequestDTO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface TALeaveRequestService {
    public boolean createTALeaveRequest(RequestDTO taLeaveRequest, int taId);
}
