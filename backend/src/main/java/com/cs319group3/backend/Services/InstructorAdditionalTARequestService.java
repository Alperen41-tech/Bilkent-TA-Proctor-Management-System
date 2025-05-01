package com.cs319group3.backend.Services;


import com.cs319group3.backend.DTOs.RequestDTOs.RequestDTO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface InstructorAdditionalTARequestService {
    List<RequestDTO> getApprovedInstructorAdditionalTARequests(@RequestParam int receiverId);
    List<RequestDTO> getUnapprovedInstructorAdditionalTARequests(@RequestParam int receiverId);
}
