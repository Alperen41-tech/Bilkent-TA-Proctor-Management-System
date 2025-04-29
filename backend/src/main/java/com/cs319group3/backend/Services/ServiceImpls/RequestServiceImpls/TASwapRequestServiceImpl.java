package com.cs319group3.backend.Services.ServiceImpls.RequestServiceImpls;


import com.cs319group3.backend.DTOMappers.RequestMappers.RequestMapper;
import com.cs319group3.backend.DTOs.RequestDTOs.RequestDTO;
import com.cs319group3.backend.DTOs.RequestDTOs.TASwapRequestDTO;
import com.cs319group3.backend.Entities.RelationEntities.ClassProctoringTARelation;
import com.cs319group3.backend.Entities.RequestEntities.Request;
import com.cs319group3.backend.Entities.RequestEntities.TASwapRequest;
import com.cs319group3.backend.Entities.UserEntities.TA;
import com.cs319group3.backend.Repositories.ClassProctoringTARelationRepo;
import com.cs319group3.backend.Repositories.TARepo;
import com.cs319group3.backend.Repositories.TASwapRequestRepo;
import com.cs319group3.backend.Services.TASwapRequestService;
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
    TASwapRequestRepo taswapRequestRepo;
    @Autowired
    ClassProctoringTARelationRepo classProctoringTARelationRepo;
    @Autowired
    TARepo taRepo;



    @Override
    public ResponseEntity<List<RequestDTO>> getTASwapRequestsByReceiver(int TAId) {

        List<TASwapRequest> swapRequests = taswapRequestRepo.findByReceiverUser_UserId(TAId);
        return new ResponseEntity<>(RequestMapper.taSwapRequestMapper(swapRequests),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<RequestDTO>> getTASwapRequestsBySender(int TAId) {
        List<TASwapRequest> swapRequests = taswapRequestRepo.findBySenderUser_UserId(TAId);
        return new ResponseEntity<>(RequestMapper.taSwapRequestMapper(swapRequests),HttpStatus.OK);
    }


    @Override
    public ResponseEntity<Boolean> createSwapRequest(RequestDTO swapRequestReceived) {
        try {
            TASwapRequest swapRequest = RequestMapper.taSwapRequestToEntityMapper(swapRequestReceived);
            taswapRequestRepo.save(swapRequest);

            return ResponseEntity.ok(true);
        } catch (Exception e) {
            e.printStackTrace(); // for debugging, or better, log it properly
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }
    }

    @Override
    public ResponseEntity<Boolean> acceptSwapRequest(int requestId) throws Exception{
        Optional<TASwapRequest> swapRequest = taswapRequestRepo.findById(requestId);

        if (!swapRequest.isPresent()) {
            throw new Exception("request with id" + requestId + " could not be found");
        }

        if (swapRequest.get().getIsApproved()){
            throw new Exception("request with id" + requestId + " is already approved");
        }

        TASwapRequest swapRequestReceived = swapRequest.get();

        try {
            setClassProctorings(swapRequestReceived);
            swapRequestReceived.setIsApproved(true);
            swapRequestReceived.setResponseDate(LocalDateTime.now());
            taswapRequestRepo.save(swapRequestReceived);
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(false,HttpStatus.BAD_REQUEST);
        }
    }

    private void setClassProctorings(TASwapRequest swapRequest) throws Exception{

        Optional<ClassProctoringTARelation> relation = classProctoringTARelationRepo
                .findById_ClassProctoringIdAndId_TAId(swapRequest.getClassProctoring().getClassProctoringId(), swapRequest.getSenderUser().getUserId());

        if (!relation.isPresent()) {
            throw new Exception("TA sent a request to the classProctoring that doesn't have connection with class proctoring");
        }

        if (relation.get().isComplete()){
            throw new Exception("ClassProctoring is already completed");
        }

        Optional<TA> newTA = taRepo.findByUserId(swapRequest.getReceiverUser().getUserId());

        if (!newTA.isPresent()) {
            throw new Exception("Receiver ta is not present");
        }

        Optional<ClassProctoringTARelation> relation2 = classProctoringTARelationRepo
                .findById_ClassProctoringIdAndId_TAId(swapRequest.getClassProctoring().getClassProctoringId(), swapRequest.getReceiverUser().getUserId());

        if (relation2.isPresent()) {
            throw new Exception("Receiver ta is already on the class proctoring, cannot perform swap");
        }

        TA newTAReceived = newTA.get();
        ClassProctoringTARelation classProctoringRel = relation.get();
        classProctoringTARelationRepo.delete(classProctoringRel);
        classProctoringRel.setTA(newTAReceived);
        classProctoringTARelationRepo.save(classProctoringRel);
    }


}

