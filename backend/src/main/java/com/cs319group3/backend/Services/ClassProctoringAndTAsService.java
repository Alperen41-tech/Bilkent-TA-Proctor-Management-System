package com.cs319group3.backend.Services;

import com.cs319group3.backend.DTOs.ClassProctoringAndTAsDTO;
import com.cs319group3.backend.Entities.ClassProctoring;

import java.util.List;

public interface ClassProctoringAndTAsService {

    /**
     * Retrieves all class proctorings and their assigned TAs for the department of the given user.
     * @param userId ID of the user whose department will be used
     * @return List of ClassProctoringAndTAsDTO representing the department's class proctorings
     */
    List<ClassProctoringAndTAsDTO> getDepartmentTAsClassProctorings(int userId);

    /**
     * Retrieves all class proctorings for a department by its code.
     * @param departmentCode Code of the department
     * @return List of ClassProctoringAndTAsDTO for the given department
     */
    List<ClassProctoringAndTAsDTO> getDepartmentClassProctoringsByCode(String departmentCode);

    /**
     * Retrieves all class proctorings in the faculty of the given user.
     * @param userId ID of the user whose faculty will be used
     * @return List of ClassProctoringAndTAsDTO for the user's faculty
     */
    List<ClassProctoringAndTAsDTO> getFacultyClassProctorings(int userId);

    /**
     * Retrieves all class proctorings for a department by its ID.
     * @param departmentId ID of the department
     * @return List of ClassProctoringAndTAsDTO for the department
     */
    List<ClassProctoringAndTAsDTO> getDepartmentClassProctoringsById(int departmentId);

    /**
     * Retrieves all class proctorings created by a specific user.
     * @param creatorId ID of the user who created the class proctorings
     * @return List of ClassProctoringAndTAsDTO created by the user
     */
    List<ClassProctoringAndTAsDTO> getClassProctoringsOfCreator(int creatorId);

    /**
     * Retrieves all class proctorings assigned to a specific instructor.
     * @param instructorId ID of the instructor
     * @return List of ClassProctoringAndTAsDTO related to the instructor
     */
    List<ClassProctoringAndTAsDTO> getClassProctoringsOfInstructor(int instructorId);

    /**
     * Converts a list of ClassProctoring entities into ClassProctoringAndTAsDTOs.
     * @param proctorings List of ClassProctoring entities
     * @return List of DTOs that combine class proctorings with their TAs
     */
    List<ClassProctoringAndTAsDTO> classProctoringToClassProctoringAndTAs(List<ClassProctoring> proctorings);
}