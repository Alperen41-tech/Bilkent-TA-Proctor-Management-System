package com.cs319group3.backend.Controllers.UserControllers;

import com.cs319group3.backend.DTOs.InstructorProfileDTO;
import com.cs319group3.backend.DTOs.TaskTypeDTO;
import com.cs319group3.backend.Services.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("instructor")
@ComponentScan(basePackages = {"com.cs319group3.backend.Controllers.UserControllers"})
@CrossOrigin(origins = "http://localhost:3000")
public class InstructorContoller {

    @Autowired
    private InstructorService instructorServiceImpl;

    @GetMapping("profile")
    public InstructorProfileDTO getInstructorProfile(@RequestParam("id") int id){
        System.out.println("request received");
        return instructorServiceImpl.getInstructorProfileById(id);
    }

    @PostMapping("createTaskType")
    public boolean createTaskType(@RequestBody TaskTypeDTO dto, @RequestParam int courseId) {
        System.out.println("Creating task type.");
        return instructorServiceImpl.createTaskType(dto, courseId);
    }

    @DeleteMapping("deleteTaskType")
    public boolean deleteTaskType(@RequestParam int courseId, @RequestParam String taskTypeName) {
        System.out.println("Deleting task type.");
        return instructorServiceImpl.deleteTaskType(courseId, taskTypeName);
    }
}
