package com.cs319group3.backend.DTOMappers.RequestMappers;

import com.cs319group3.backend.DTOs.RequestDTOs.RequestDTO;


import com.cs319group3.backend.Entities.ClassProctoring;
import com.cs319group3.backend.Entities.RequestEntities.*;
import com.cs319group3.backend.Entities.TaskType;
import com.cs319group3.backend.Entities.UserEntities.DepartmentSecretary;
import com.cs319group3.backend.Entities.UserEntities.User;
import com.cs319group3.backend.Entities.UserEntities.TA;

import com.cs319group3.backend.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
public class RequestMapper {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ClassProctoringRepo classProctoringRepo;

    @Autowired
    private DepartmentSecretaryRepo departmentSecretaryRepo;

    @Autowired
    private TaskTypeRepo taskTypeRepo;
    @Autowired
    private DeansOfficeRepo deansOfficeRepo;

    public void essentialToEntityMapper(Request finalRequest, RequestDTO dto) throws Exception{

        finalRequest.setSentDate(LocalDateTime.now());
        //finalRequest.setApproved(dto.getIsApproved());
        finalRequest.setDescription(dto.getDescription());

        Optional<User> senderUser = userRepo.findById(dto.getSenderId());
        Optional<User> receiverUser = userRepo.findById(dto.getReceiverId());

        if (!senderUser.isPresent()) {
            throw new RuntimeException("Sender TA cannot be found in database");
        }
        finalRequest.setSenderUser(senderUser.get());

        if (!receiverUser.isPresent()) {
            throw new RuntimeException("Receiver user cannot be found in database: " + dto.getReceiverId());
        }
        else{
            finalRequest.setReceiverUser(receiverUser.get());
        }
    }

    public TASwapRequest taSwapRequestToEntityMapper(RequestDTO dto) throws Exception{
        TASwapRequest taSwapRequest = new TASwapRequest();
        essentialToEntityMapper(taSwapRequest, dto);

        Optional<ClassProctoring> classProctoring = classProctoringRepo.findById(dto.getClassProctoringId());

        if (!classProctoring.isPresent()) {
            throw new RuntimeException("ClassProctoring cannot be found in database");
        }

        taSwapRequest.setClassProctoring(classProctoring.get());
        return taSwapRequest;
    }

    public InstructorAdditionalTARequest instructorAdditionalTARequestToEntityMapper(RequestDTO dto) throws Exception {

        InstructorAdditionalTARequest instructorAdditionalTARequest = new InstructorAdditionalTARequest();
        essentialToEntityMapper(instructorAdditionalTARequest, dto);

        Optional<ClassProctoring> classProctoring = classProctoringRepo.findById(dto.getClassProctoringId());

        if (!classProctoring.isPresent()) {
            throw new RuntimeException("ClassProctoring cannot be found in database");
        }

        instructorAdditionalTARequest.setTaCount(dto.getTaCountNeeded());
        instructorAdditionalTARequest.setSentToSecretary(false);
        instructorAdditionalTARequest.setClassProctoring(classProctoring.get());
        instructorAdditionalTARequest.setReceiverUser(classProctoring.get().getCourse().getDepartment().getFaculty().getDeansOffice());

        return instructorAdditionalTARequest;
    }

    public TALeaveRequest taLeaveRequestToEntityMapper(RequestDTO dto) throws Exception{
        TALeaveRequest taLeaveRequest = new TALeaveRequest();
        essentialToEntityMapper(taLeaveRequest, dto);

        TA senderTA = (TA) taLeaveRequest.getSenderUser();

        Optional<DepartmentSecretary> departmentSecretary = departmentSecretaryRepo
                .findByDepartment_DepartmentId(senderTA.getDepartment().getDepartmentId());

        if (!departmentSecretary.isPresent()) {
            throw new Exception("DepartmentSecretary cannot be found in database");
        }

        taLeaveRequest.setReceiverUser(departmentSecretary.get());

        taLeaveRequest.setLeaveStartDate(dto.getLeaveStartDate());
        taLeaveRequest.setLeaveEndDate(dto.getLeaveEndDate());

        return taLeaveRequest;
    }

    public TAWorkloadRequest taWorkloadRequestToEntityMapper(RequestDTO requestDTO, User receiverUser) throws Exception{
        TAWorkloadRequest taWorkloadRequest = new TAWorkloadRequest();
        requestDTO.setReceiverId(receiverUser.getUserId());
        essentialToEntityMapper(taWorkloadRequest, requestDTO);

        TA senderTA = (TA) taWorkloadRequest.getSenderUser();
        Optional<TaskType> taskType;
        if (!requestDTO.getTaskTypeName().equals("Other")) {
            taskType = taskTypeRepo.findByTaskTypeNameAndCourse_CourseId(requestDTO.getTaskTypeName(), senderTA.getAssignedCourse().getCourseId());
            if (!taskType.isPresent()) {
                throw new Exception("Task type does not exist");
            }
            taWorkloadRequest.setTaskType(taskType.get());
        }

        taWorkloadRequest.setWorkloadId(requestDTO.getWorkloadId());
        taWorkloadRequest.setCourse(senderTA.getAssignedCourse());
        taWorkloadRequest.setTimeSpent(requestDTO.getTimeSpent());
        taWorkloadRequest.setReceiverUser(receiverUser);

        return taWorkloadRequest;
    }

    public RequestDTO essentialMapper(Request request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        RequestDTO requestDTO = new RequestDTO();

        requestDTO.setRequestId(request.getRequestId());
        requestDTO.setRequestId(request.getRequestId());
        requestDTO.setRequestType(request.getClass().getSimpleName());
        requestDTO.setSenderId(request.getSenderUser().getUserId());
        requestDTO.setReceiverId(request.getReceiverUser().getUserId());
        requestDTO.setIsApproved(request.isApproved());
        requestDTO.setSentDateTime(request.getSentDate());


        LocalDateTime ldt = request.getResponseDate();
        if (ldt == null) {
            requestDTO.setResponseDateTime(null);
            requestDTO.setStatus(null);
        }
        else{
            requestDTO.setResponseDateTime(ldt);
            String currStatus;
            if (request.isApproved()) {
                currStatus = "APPROVED";
            }
            else {
                currStatus = "REJECTED";
            }
            requestDTO.setStatus(currStatus);
        }

        requestDTO.setIsApproved(request.isApproved());
        requestDTO.setDescription(request.getDescription());

        requestDTO.setSenderName(request.getSenderUser().getFullName() );
        requestDTO.setReceiverName(request.getReceiverUser().getFullName());

        requestDTO.setSenderEmail(request.getSenderUser().getEmail());
        requestDTO.setReceiverEmail(request.getReceiverUser().getEmail());


        return requestDTO;
    }


    public RequestDTO taSwapRequestMapper(TASwapRequest request) {

        RequestDTO requestDTO = essentialMapper(request);
        classProctoringMapperHelper(requestDTO, request.getClassProctoring());
        return requestDTO;
    }


    public List<RequestDTO> taSwapRequestMapper(List<TASwapRequest> requests) {
        List<RequestDTO> requestDTOs = new ArrayList<>();
        for (TASwapRequest request : requests) {
            requestDTOs.add(taSwapRequestMapper(request));
        }

        return requestDTOs;
    }


    public RequestDTO authStaffTARequestMapper(AuthStaffProctoringRequest request){
        RequestDTO requestDTO = essentialMapper(request);
        classProctoringMapperHelper(requestDTO, request.getClassProctoring());
        return requestDTO;
    }


    public List<RequestDTO> authStaffTARequestMapper(List<AuthStaffProctoringRequest> requests) {

        List<RequestDTO> requestDTOs = new ArrayList<>();
        for(AuthStaffProctoringRequest request : requests){
            requestDTOs.add(authStaffTARequestMapper(request));
        }
        return requestDTOs;
    }

    @Autowired
    InstructorRepo instructorRepo;

    public RequestDTO instructorAdditionalTARequestMapper(InstructorAdditionalTARequest request){
        RequestDTO requestDTO = essentialMapper(request);
        classProctoringMapperHelper(requestDTO, request.getClassProctoring());

        requestDTO.setTaCountNeeded(request.getTaCount());
        requestDTO.setIsComplete(request.isSentToSecretary());
        requestDTO.setDepartmentName(request.getClassProctoring().getCourse().getDepartment().getDepartmentName());
        requestDTO.setCourseName(request.getClassProctoring().getCourse().getCourseName());
        requestDTO.setCourseCode(request.getClassProctoring().getCourse().getCourseFullCode());

        int senderUserId = request.getSenderUser().getUserId();
        Integer departmentId = instructorRepo.getDepartmentId(senderUserId);
        if (departmentId != null) {
            requestDTO.setDepartmentId(departmentId);
        }

        return requestDTO;
    }



    public List<RequestDTO> instructorAdditionalTARequestMapper(List<InstructorAdditionalTARequest> requests) {
        List<RequestDTO> requestDTOs = new ArrayList<>();
        for(InstructorAdditionalTARequest request : requests){
            requestDTOs.add(instructorAdditionalTARequestMapper(request));
        }
        return requestDTOs;
    }

    public RequestDTO taLeaveRequestMapper(TALeaveRequest request){
        RequestDTO requestDTO = essentialMapper(request);
        requestDTO.setIsUrgent(request.isUrgent());
        requestDTO.setLeaveStartDate(request.getLeaveStartDate());
        requestDTO.setLeaveEndDate(request.getLeaveEndDate());
        return requestDTO;
    }

    public List<RequestDTO> taLeaveRequestMapper(List<TALeaveRequest> requests) {
        List<RequestDTO> requestDTOs = new ArrayList<>();
        for(TALeaveRequest request : requests){
            requestDTOs.add(taLeaveRequestMapper(request));
        }
        return requestDTOs;
    }

    public RequestDTO taWorkloadRequestMapper(TAWorkloadRequest request){
        RequestDTO requestDTO = essentialMapper(request);
        if (request.getTaskType() == null)
            requestDTO.setTaskTypeName("Other");
        else
            requestDTO.setTaskTypeName(request.getTaskType().getTaskTypeName());
        requestDTO.setTimeSpent(request.getTimeSpent());
        requestDTO.setCourseCode(request.getCourse().getCourseFullCode());
        return requestDTO;
    }


    public List<RequestDTO> taWorkloadRequestMapper(List<TAWorkloadRequest> requests){
        List<RequestDTO> requestDTOs = new ArrayList<>();
        for(TAWorkloadRequest request : requests){
            requestDTOs.add(taWorkloadRequestMapper(request));
        }
        return requestDTOs;
    }


    private void classProctoringMapperHelper(RequestDTO requestDTO, ClassProctoring proctoring){

        requestDTO.setClassProctoringId(proctoring.getClassProctoringId());
        requestDTO.setClassProctoringEventName(proctoring.getEventName());
        requestDTO.setClassProctoringStartDate(proctoring.getStartDate());
        requestDTO.setClassProctoringEndDate(proctoring.getEndDate());
    }

    public static Request toEntity(RequestDTO requestDTO) {
        Request request = new Request();
        request.setResponseDate(requestDTO.getResponseDateTime());
        request.setApproved(requestDTO.getIsApproved());
        request.setSentDate(requestDTO.getSentDateTime());
        request.setDescription(requestDTO.getDescription());
        return request;
    }


}
