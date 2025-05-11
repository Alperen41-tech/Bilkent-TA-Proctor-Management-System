package com.cs319group3.backend.Services.ServiceImpls;

import com.cs319group3.backend.DTOMappers.RequestMappers.RequestMapper;
import com.cs319group3.backend.DTOs.RequestDTOs.RequestDTO;
import com.cs319group3.backend.Entities.ClassProctoring;
import com.cs319group3.backend.Entities.ProctoringApplication;
import com.cs319group3.backend.Entities.RequestEntities.InstructorAdditionalTARequest;
import com.cs319group3.backend.Entities.UserEntities.DeansOffice;
import com.cs319group3.backend.Entities.UserEntities.Instructor;
import com.cs319group3.backend.Enums.NotificationType;
import com.cs319group3.backend.Repositories.ClassProctoringRepo;
import com.cs319group3.backend.Repositories.InstructorAdditionalTARequestRepo;
import com.cs319group3.backend.Repositories.InstructorRepo;
import com.cs319group3.backend.Repositories.ProctoringApplicationRepo;
import com.cs319group3.backend.Services.InstructorAdditionalTARequestService;
import com.cs319group3.backend.Services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InstructorAdditionalTARequestServiceImpl implements InstructorAdditionalTARequestService {

    @Autowired
    private InstructorAdditionalTARequestRepo instructorAdditionalTARequestRepo;

    @Autowired
    private RequestMapper requestMapper;

    @Autowired
    private InstructorRepo instructorRepo;

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private ClassProctoringRepo classProctoringRepo;
    @Autowired
    private ProctoringApplicationRepo proctoringApplicationRepo;

    @Override
    public List<RequestDTO> getApprovedInstructorAdditionalTARequests(int receiverId) {
        List<InstructorAdditionalTARequest> list = instructorAdditionalTARequestRepo.findByReceiverIdAndIsApprovedTrue(receiverId);
        List<RequestDTO> dtos = new ArrayList<>();
        for (InstructorAdditionalTARequest request : list) {
            dtos.add(requestMapper.instructorAdditionalTARequestMapper(request));
        }
        return dtos;
    }

    @Override
    public List<RequestDTO> getUnapprovedInstructorAdditionalTARequests(int receiverId) {
        List<InstructorAdditionalTARequest> list = instructorAdditionalTARequestRepo.findByReceiverIdAndIsApprovedFalseAndResponseDateNull(receiverId);
        System.out.println("Here is the size of the list: " + list.size());
        List<RequestDTO> dtos = new ArrayList<>();
        for (InstructorAdditionalTARequest request : list) {
            dtos.add(requestMapper.instructorAdditionalTARequestMapper(request));
        }
        System.out.println("Here is the size of the new list: " + dtos.size());
        return dtos;
    }

    @Override
    public boolean createInstructorAdditionalTARequest(RequestDTO requestDTO, int senderId) throws Exception {
        int classProctoringId = requestDTO.getClassProctoringId();
        List<ProctoringApplication> list = proctoringApplicationRepo.findByClassProctoring_ClassProctoringId(classProctoringId);

        for(ProctoringApplication application : list) {
            if(!application.isComplete()){
                throw new RuntimeException("A proctoring application for the requested class proctoring is going on");
            }
        }

        requestDTO.setSenderId(senderId);

        Optional<Instructor> instructor = instructorRepo.findById(senderId);
        if (instructor.isEmpty()) {
            throw new RuntimeException("Instructor not found, something went wrong");
        }

        DeansOffice deansOffice = instructor.get().getDepartment().getFaculty().getDeansOffice();

        requestDTO.setReceiverId(deansOffice.getUserId());
        InstructorAdditionalTARequest request = requestMapper.instructorAdditionalTARequestToEntityMapper(requestDTO);

        instructorAdditionalTARequestRepo.save(request);
        notificationService.createNotification(request, NotificationType.REQUEST);

        return true;
    }
}