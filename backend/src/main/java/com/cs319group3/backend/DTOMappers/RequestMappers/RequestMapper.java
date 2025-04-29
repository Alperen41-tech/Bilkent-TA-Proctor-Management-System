package com.cs319group3.backend.DTOMappers.RequestMappers;

import com.cs319group3.backend.DTOs.RequestDTOs.RequestDTO;

import com.cs319group3.backend.DTOs.RequestDTOs.TASwapRequestDTO;
import com.cs319group3.backend.Entities.ClassProctoring;
import com.cs319group3.backend.Entities.RequestEntities.*;
import com.cs319group3.backend.Entities.UserEntities.User;
import com.cs319group3.backend.Repositories.ClassProctoringRepo;
import com.cs319group3.backend.Repositories.UserRepo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class RequestMapper {

    private static UserRepo userRepo;
    private static ClassProctoringRepo classProctoringRepo;

    public static RequestDTO essentialMapper(Request request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        RequestDTO requestDTO = new RequestDTO();

        requestDTO.setRequestId(request.getRequestId());
        requestDTO.setRequestType(request.getClass().getSimpleName());
        requestDTO.setSenderId(request.getSenderUser().getUserId());
        requestDTO.setReceiverId(request.getReceiverUser().getUserId());
        requestDTO.setIsApproved(request.getIsApproved());
        requestDTO.setSentDateTime(request.getSentDate());


        LocalDateTime ldt = request.getResponseDate();
        if (ldt == null) {
            requestDTO.setResponseDateTime(null);
            requestDTO.setStatus(null);
        }
        else{
            requestDTO.setResponseDateTime(ldt);
            String currStatus;
            if (request.getIsApproved()) {
                currStatus = "APPROVED";
            }
            else {
                currStatus = "REJECTED";
            }
            requestDTO.setStatus(currStatus);
        }

        requestDTO.setIsApproved(request.getIsApproved());
        requestDTO.setDescription(request.getDescription());

        requestDTO.setSenderName(request.getSenderUser().getName());
        requestDTO.setReceiverName(request.getReceiverUser().getName());

        requestDTO.setSenderEmail(request.getSenderUser().getEmail());
        requestDTO.setReceiverEmail(request.getReceiverUser().getEmail());


        return requestDTO;
    }

    public static void essentialToEntityMapper(Request finalRequest, RequestDTO dto) throws Exception{

        finalRequest.setSentDate(dto.getSentDateTime());
        finalRequest.setResponseDate(dto.getResponseDateTime());
        finalRequest.setIsApproved(dto.getIsApproved());
        finalRequest.setDescription(dto.getDescription());

        Optional<User> senderUser = userRepo.findById(dto.getSenderId());
        Optional<User> receiverUser = userRepo.findById(dto.getReceiverId());

        if (senderUser.isPresent()) {
            throw new Exception("Sender TA cannot be found in database");
        }
        if (receiverUser.isPresent()) {
            throw new Exception("Receiver TA cannot be found in database");
        }

        finalRequest.setSenderUser(senderUser.get());
        finalRequest.setReceiverUser(receiverUser.get());
    }

    public static TASwapRequest taSwapRequestToEntityMapper(RequestDTO dto) throws Exception{
        TASwapRequest taSwapRequest = new TASwapRequest();
        try{
            essentialToEntityMapper(taSwapRequest, dto);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        Optional<ClassProctoring> classProctoring = classProctoringRepo.findById(dto.getClassProctoringId());

        if (!classProctoring.isPresent()) {
            throw new Exception("ClassProctoring cannot be found in database");
        }

        taSwapRequest.setClassProctoring(classProctoring.get());

        return taSwapRequest;
    }







    public static RequestDTO taSwapRequestMapper(TASwapRequest request) {

        RequestDTO requestDTO = essentialMapper(request);
        classProctoringMapperHelper(requestDTO, request.getClassProctoring());
        return requestDTO;
    }

    public static List<RequestDTO> taSwapRequestMapper(List<TASwapRequest> requests) {
        return requests.stream()
                .map(RequestMapper::taSwapRequestMapper)
                .collect(Collectors.toList());
    }


    public static RequestDTO authStaffTARequestMapper(AuthStaffProctoringRequest request){
        RequestDTO requestDTO = essentialMapper(request);
        classProctoringMapperHelper(requestDTO, request.getClassProctoring());
        return requestDTO;
    }


    public static List<RequestDTO> authStaffTARequestMapper(List<AuthStaffProctoringRequest> requests) {
        return requests.stream()
                .map(RequestMapper::authStaffTARequestMapper)
                .collect(Collectors.toList());
    }


    public static RequestDTO instructorAdditionalTARequestMapper(InstructorAdditionalTARequest request){
        RequestDTO requestDTO = essentialMapper(request);
        classProctoringMapperHelper(requestDTO, request.getClassProctoring());

        requestDTO.setTaCountNeeded(request.getTaCount());
        requestDTO.setIsComplete(request.isComplete());

        return requestDTO;
    }

    public static List<RequestDTO> instructorAdditionalTARequestMapper(List<InstructorAdditionalTARequest> requests) {
        return requests.stream()
                .map(RequestMapper::instructorAdditionalTARequestMapper)
                .collect(Collectors.toList());
    }

    public static RequestDTO taLeaveRequestMapper(TALeaveRequest request){
        RequestDTO requestDTO = essentialMapper(request);
        requestDTO.setIsUrgent(request.isUrgent());
        requestDTO.setLeaveStartDate(request.getLeaveStartDate());
        requestDTO.setLeaveEndDate(request.getLeaveEndDate());
        return requestDTO;
    }

    public static List<RequestDTO> taLeaveRequestMapper(List<TALeaveRequest> requests) {
        return requests.stream()
                .map(RequestMapper::taLeaveRequestMapper)
                .collect(Collectors.toList());
    }

    public static RequestDTO taWorkloadRequestMapper(TAWorkloadRequest request){
        RequestDTO requestDTO = essentialMapper(request);
        requestDTO.setTaskTypeName(request.getTaskType().getTaskTypeName());
        requestDTO.setTimeSpent(request.getTimeSpent());
        requestDTO.setCourseCode(request.getCourse().getDepartmentCourseCode());
        return requestDTO;
    }


    public static List<RequestDTO> taWorkloadRequestMapper(List<TAWorkloadRequest> requests){
        return requests.stream()
                .map(RequestMapper:: taWorkloadRequestMapper)
                .collect(Collectors.toList());
    }


    private static void classProctoringMapperHelper(RequestDTO requestDTO, ClassProctoring proctoring){

        requestDTO.setClassProctoringId(proctoring.getClassProctoringId());
        requestDTO.setClassProctoringEventName(proctoring.getEventName());
        requestDTO.setClassProctoringStartDate(proctoring.getStartDate());
        requestDTO.setClassProctoringEndDate(proctoring.getEndDate());

    }


}
