package com.cs319group3.backend.Services.ServiceImpls.RequestServiceImpls;

import com.cs319group3.backend.DTOMappers.RequestMappers.RequestMapper;
import com.cs319group3.backend.DTOMappers.TAProfileMapper;
import com.cs319group3.backend.DTOs.RequestDTOs.RequestDTO;
import com.cs319group3.backend.DTOs.TAProfileDTO;
import com.cs319group3.backend.Entities.ClassProctoring;
import com.cs319group3.backend.Entities.Department;
import com.cs319group3.backend.Entities.RelationEntities.ClassProctoringTARelation;
import com.cs319group3.backend.Entities.RequestEntities.TASwapRequest;
import com.cs319group3.backend.Entities.UserEntities.TA;
import com.cs319group3.backend.Enums.LogType;
import com.cs319group3.backend.Enums.NotificationType;
import com.cs319group3.backend.Repositories.*;
import com.cs319group3.backend.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TASwapRequestServiceImpl implements TASwapRequestService {

    @Autowired
    private TASwapRequestRepo taswapRequestRepo;

    @Autowired
    private ClassProctoringTARelationRepo classProctoringTARelationRepo;

    @Autowired
    private TARepo taRepo;

    @Autowired
    private ClassProctoringRepo classProctoringRepo;

    @Autowired
    private RequestMapper requestMapper;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private LogService logService;

    @Autowired
    private TAService taService;

    @Autowired
    private TAProfileMapper taProfileMapper;

    @Autowired
    private TAAvailabilityService taAvailabilityService;

    @Override
    public ResponseEntity<List<RequestDTO>> getTASwapRequestsByReceiver(int TAId) {
        List<TASwapRequest> swapRequests = taswapRequestRepo.findByReceiverUser_UserId(TAId);
        return new ResponseEntity<>(requestMapper.taSwapRequestMapper(swapRequests), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<RequestDTO>> getTASwapRequestsBySender(int TAId) {
        List<TASwapRequest> swapRequests = taswapRequestRepo.findBySenderUser_UserId(TAId);
        return new ResponseEntity<>(requestMapper.taSwapRequestMapper(swapRequests), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Boolean> createSwapRequest(RequestDTO swapRequestReceived) throws Exception {
        TASwapRequest swapRequest = requestMapper.taSwapRequestToEntityMapper(swapRequestReceived);
        taswapRequestRepo.save(swapRequest);
        String logMessage = "User " + swapRequest.getSenderUser().getUserId() + " sent a swap request (" +
                swapRequest.getRequestId() + ") to user " + swapRequest.getReceiverUser().getUserId() + ".";
        logService.createLog(logMessage, LogType.CREATE);
        notificationService.createNotification(swapRequest, NotificationType.REQUEST);
        return ResponseEntity.ok(true);
    }

    @Override
    public ResponseEntity<Boolean> acceptSwapRequest(int requestId) throws Exception {
        Optional<TASwapRequest> swapRequest = taswapRequestRepo.findById(requestId);
        if (swapRequest.isEmpty()) {
            throw new Exception("request with id " + requestId + " could not be found");
        }
        if (swapRequest.get().isApproved()) {
            throw new Exception("request with id " + requestId + " is already approved");
        }

        TASwapRequest swapRequestReceived = swapRequest.get();

        setClassProctorings(swapRequestReceived);
        swapRequestReceived.setApproved(true);
        swapRequestReceived.setResponseDate(LocalDateTime.now());
        taswapRequestRepo.save(swapRequestReceived);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @Override
    public List<TAProfileDTO> getAvailableTAProfilesForClassProctoring(int classProctoringId, int taId) throws Exception {
        Optional<ClassProctoring> classProctoring = classProctoringRepo.findById(classProctoringId);
        if (classProctoring.isEmpty()) {
            throw new Exception("classProctoring with id " + classProctoringId + " could not be found");
        }

        ClassProctoring classProctoringReceived = classProctoring.get();
        Department currDep = classProctoringReceived.getCourse().getDepartment();
        List<TA> departmentAvailableTAs = taRepo.findAvailableTAsByDepartment(currDep.getDepartmentCode(), classProctoringId);

        departmentAvailableTAs.removeIf(ta ->
                !taAvailabilityService.isTAAvailable(ta, classProctoringReceived)
                        || isRequestAlreadySent(taId, ta, classProctoringReceived));

        return taProfileMapper.essentialMapper(departmentAvailableTAs);
    }

    @Override
    public boolean isRequestAlreadySent(int senderId, TA receiver, ClassProctoring ctr) {
        Optional<TASwapRequest> taSwapRequest = taswapRequestRepo
                .findBySenderUser_UserIdAndReceiverUser_UserIdAndClassProctoring_ClassProctoringId(
                        senderId, receiver.getUserId(), ctr.getClassProctoringId());

        return taSwapRequest.isPresent();
    }

    private void setClassProctorings(TASwapRequest swapRequest) throws Exception {
        Optional<ClassProctoringTARelation> relation = classProctoringTARelationRepo
                .findById_ClassProctoringIdAndId_TAId(
                        swapRequest.getClassProctoring().getClassProctoringId(),
                        swapRequest.getSenderUser().getUserId());

        if (relation.isEmpty()) {
            throw new Exception("TA sent a request to a classProctoring not connected to them");
        }
        if (relation.get().isComplete()) {
            throw new Exception("ClassProctoring is already completed");
        }

        Optional<TA> newTA = taRepo.findByUserId(swapRequest.getReceiverUser().getUserId());
        if (newTA.isEmpty()) {
            throw new Exception("Receiver TA not found");
        }

        Optional<ClassProctoringTARelation> relation2 = classProctoringTARelationRepo
                .findById_ClassProctoringIdAndId_TAId(
                        swapRequest.getClassProctoring().getClassProctoringId(),
                        swapRequest.getReceiverUser().getUserId());

        if (relation2.isPresent()) {
            throw new Exception("Receiver TA is already assigned to this class proctoring");
        }

        TA newTAReceived = newTA.get();
        ClassProctoringTARelation classProctoringRel = relation.get();

        classProctoringTARelationRepo.delete(classProctoringRel);
        classProctoringRel.setTA(newTAReceived);
        classProctoringTARelationRepo.save(classProctoringRel);
    }
}