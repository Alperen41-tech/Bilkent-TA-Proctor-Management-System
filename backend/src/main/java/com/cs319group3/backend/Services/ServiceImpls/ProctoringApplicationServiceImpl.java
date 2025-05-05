package com.cs319group3.backend.Services.ServiceImpls;

import com.cs319group3.backend.DTOMappers.ProctoringApplicationMapper;
import com.cs319group3.backend.DTOs.ProctoringApplicationDTO;
import com.cs319group3.backend.Entities.ClassProctoring;
import com.cs319group3.backend.Entities.Department;
import com.cs319group3.backend.Entities.ProctoringApplication;
import com.cs319group3.backend.Entities.RelationEntities.ProctoringApplicationTARelation;
import com.cs319group3.backend.Entities.RequestEntities.Request;
import com.cs319group3.backend.Entities.UserEntities.TA;
import com.cs319group3.backend.Enums.LogType;
import com.cs319group3.backend.Enums.NotificationType;
import com.cs319group3.backend.Enums.ProctoringApplicationType;
import com.cs319group3.backend.Repositories.*;
import com.cs319group3.backend.Services.LogService;
import com.cs319group3.backend.Services.NotificationService;
import com.cs319group3.backend.Services.ProctoringApplicationService;
import com.cs319group3.backend.Services.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProctoringApplicationServiceImpl implements ProctoringApplicationService {
    @Autowired
    private ProctoringApplicationRepo proctoringApplicationRepo;
    @Autowired
    private DepartmentRepo departmentRepo;
    @Autowired
    private ProctoringApplicationMapper proctoringApplicationMapper;
    @Autowired
    private TARepo taRepo;
    @Autowired
    private ProctoringApplicationTARelationRepo proctoringApplicationTARelationRepo;
    @Autowired
    LogService logService;

    @Override
    public List<ProctoringApplicationDTO> getProctoringApplications(int deansOfficeId){
        List<ProctoringApplication> listPA = proctoringApplicationRepo.findByDeansOfficeId(deansOfficeId);
        List<ProctoringApplicationDTO> dtoPA = new ArrayList<>();
        for(ProctoringApplication proctoringApplication:listPA){
            ProctoringApplicationDTO dto = proctoringApplicationMapper.toDTO(proctoringApplication);
            //dto.setClassProctoringDTO(ClassProctoringMapper.essentialMapper(proctoringApplication.getClassProctoring()));
            dtoPA.add(dto);
        }
        return dtoPA;
    }

    @Autowired
    ClassProctoringRepo classProctoringRepo;

    @Autowired
    RequestService requestService;

    @Autowired
    NotificationService notificationService;

    //IMPORTANT!!! Given dto must contain only the fields visibleDepartmentId and taCountLimit.
    @Override
    public boolean createProctoringApplication(int classProctoringId, ProctoringApplicationDTO dto, int deansOfficeId) {
        System.out.println("Tekil: "+classProctoringId);
        ProctoringApplication proctoringApplication = new ProctoringApplication();

        Optional<ClassProctoring> classProctoring = classProctoringRepo.findById(classProctoringId);
        if(classProctoring.isEmpty()){
            System.out.println("girme1: "+classProctoringId);
            return false;
        }
        proctoringApplication.setClassProctoring(classProctoring.get());

        Optional<Department> department = departmentRepo.findById(dto.getVisibleDepartmentId());
        if(department.isEmpty()){
            System.out.println("girme2 "+dto.getVisibleDepartmentId());
            return false;
        }
        proctoringApplication.setVisibleDepartment(department.get());

        proctoringApplication.setComplete(false);
        proctoringApplication.setApplicationType(ProctoringApplicationType.NOT_DEFINED);

        LocalDateTime cpTime = proctoringApplication.getClassProctoring().getStartDate();
        proctoringApplication.setFinishDate(cpTime.minusDays(3));

        proctoringApplication.setApplicantCountLimit(dto.getApplicantCountLimit());
        String logMessage = "User " + deansOfficeId + " created proctoring application " + proctoringApplication.getApplicationId() + ".";
        logService.createLog(logMessage, LogType.CREATE);
        proctoringApplicationRepo.save(proctoringApplication);

        Request request = requestService.createProctoringApplicationRequest(dto, deansOfficeId);
        notificationService.createNotification(request, NotificationType.APPROVAL);
        return true;
    }

    @Override
    public boolean createProctoringApplications(int classProctoringId, List<ProctoringApplicationDTO> dto, int deansOfficeId) {
        System.out.println("Çoğul");
        for(ProctoringApplicationDTO proctoringApplicationDTO : dto){
            if(!createProctoringApplication(classProctoringId, proctoringApplicationDTO, deansOfficeId)){
                return false;
            }
        }
        return true;
    }

    @Override
    public List<ProctoringApplicationDTO> getAllApplicationsByDepartment(int departmentId) {
        List<ProctoringApplication> listPA = proctoringApplicationRepo.findByVisibleDepartment_DepartmentId(departmentId);
        return proctoringApplicationMapper.toDTO(listPA);
    }

    @Override
    public boolean setApplicationType(int applicationId, ProctoringApplicationType type) {
        Optional<ProctoringApplication> proctoringApplication = proctoringApplicationRepo.findById(applicationId);
        if(!proctoringApplication.isPresent()){
            throw new RuntimeException("no such proctoring application found");
        }

        ProctoringApplication proctoringApplicationReceieved = proctoringApplication.get();

        if (proctoringApplicationReceieved.getApplicationType() != ProctoringApplicationType.NOT_DEFINED){
            throw new RuntimeException("already defined proctoring application type");
        }

        proctoringApplicationReceieved.setApplicationType(type);

        String logMessage = "Application type " + type + " set to " + proctoringApplicationReceieved.getApplicationId() + ".";
        logService.createLog(logMessage, LogType.UPDATE);
        proctoringApplicationRepo.save(proctoringApplicationReceieved);
        return true;
    }

    @Override
    public List<ProctoringApplicationDTO> getAllApplicationsForTA(int userId, ProctoringApplicationType applicationType) {
        Optional<TA> ta = taRepo.findByUserId(userId);
        if (!ta.isPresent())
            throw new RuntimeException("no such ta");


        TA taReceived = ta.get();
        List<ProctoringApplication> allProctorings = proctoringApplicationRepo.findByVisibleDepartment_DepartmentIdNotAndApplicationType(taReceived.getDepartment().getDepartmentId(), applicationType);

        List<ProctoringApplicationDTO> proctoringApplicationDTOs = proctoringApplicationMapper.toDTO(allProctorings);

        for (ProctoringApplicationDTO proctoringApplicationDTO : proctoringApplicationDTOs) {

            Optional<ProctoringApplicationTARelation> patr = proctoringApplicationTARelationRepo.findByTA_UserIdAndProctoringApplication_ApplicationId(taReceived.getUserId(), proctoringApplicationDTO.getApplicationId());
            if (patr.isPresent()){
                proctoringApplicationDTO.setIsAppliedByTA(true);
            }
            else {
                proctoringApplicationDTO.setIsAppliedByTA(false);
            }
        }

        return proctoringApplicationDTOs;
    }
}
