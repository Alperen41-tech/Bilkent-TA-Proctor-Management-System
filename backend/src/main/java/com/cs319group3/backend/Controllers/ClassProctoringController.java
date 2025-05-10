package com.cs319group3.backend.Controllers;

import com.cs319group3.backend.Components.CurrentUserUtil;
import com.cs319group3.backend.DTOs.ClassProctoringDTO;
import com.cs319group3.backend.DTOs.CreateClassProctoringDTO;
import com.cs319group3.backend.Services.ClassProctoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsible for handling operations related to class proctoring events,
 * including creation and listing of all class proctorings.
 */
@RestController
@RequestMapping("classProctoring")
@CrossOrigin(origins = "http://localhost:3000")
public class ClassProctoringController {

    @Autowired
    private ClassProctoringService classProctoringService;
    @Autowired
    private CurrentUserUtil currentUserUtil;

    /**
     * Creates a new class proctoring entry.
     *
     * @param dto the DTO containing class proctoring details
     * @return true if creation is successful
     */
    @PostMapping("createClassProctoring")
    public boolean createClassProctoring(@RequestBody CreateClassProctoringDTO dto) {
        int userId = currentUserUtil.getCurrentUserId();
        return classProctoringService.createClassProctoring(dto, userId);
    }

    /**
     * Retrieves all class proctoring records in the system.
     *
     * @return a list of ClassProctoringDTOs
     */
    @GetMapping("getAllClassProctorings")
    public List<ClassProctoringDTO> getAllClassProctoring() {
        return classProctoringService.getAllClassProctoring();
    }
}