package com.cs319group3.backend.Services;

import com.cs319group3.backend.DTOs.InstructorAdditionalTARequestDTO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface InstructorAdditionalTARequestService {
    List<InstructorAdditionalTARequestDTO> getInstructorAdditionalTARequests(@RequestParam int receiverId);
}
