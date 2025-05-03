package com.cs319group3.backend.Services.ServiceImpls;

import com.cs319group3.backend.Controllers.ProctoringApplicationController;
import com.cs319group3.backend.DTOMappers.ClassProctoringMapper;
import com.cs319group3.backend.DTOMappers.ProctoringApplicationMapper;
import com.cs319group3.backend.DTOs.ProctoringApplicationDTO;
import com.cs319group3.backend.Entities.ClassProctoring;
import com.cs319group3.backend.Entities.Department;
import com.cs319group3.backend.Entities.ProctoringApplication;
import com.cs319group3.backend.Entities.RequestEntities.Request;
import com.cs319group3.backend.Enums.NotificationType;
import com.cs319group3.backend.Repositories.ClassProctoringRepo;
import com.cs319group3.backend.Repositories.DepartmentRepo;
import com.cs319group3.backend.Repositories.ProctoringApplicationRepo;
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
        ProctoringApplication proctoringApplication = new ProctoringApplication();

        Optional<ClassProctoring> classProctoring = classProctoringRepo.findById(classProctoringId);
        if(classProctoring.isEmpty()){
            return false;
        }
        proctoringApplication.setClassProctoring(classProctoring.get());

        Optional<Department> department = departmentRepo.findById(dto.getVisibleDepartmentId());
        if(department.isEmpty()){
            return false;
        }
        proctoringApplication.setVisibleDepartment(department.get());

        proctoringApplication.setComplete(false);
        proctoringApplication.setVisibleForTAs(false);

        LocalDateTime cpTime = proctoringApplication.getClassProctoring().getStartDate();
        proctoringApplication.setFinishDate(cpTime.minusDays(3));

        proctoringApplication.setApplicantCountLimit(dto.getApplicantCountLimit());
        proctoringApplicationRepo.save(proctoringApplication);

        Request request = requestService.createProctoringApplicationRequest(dto, deansOfficeId);
        notificationService.createNotification(request, NotificationType.APPROVAL);
        return true;
    }

    @Override
    public boolean createProctoringApplications(int classProctoringId, List<ProctoringApplicationDTO> dto, int deansOfficeId) {
        for(ProctoringApplicationDTO proctoringApplicationDTO : dto){
            if(!createProctoringApplication(classProctoringId, proctoringApplicationDTO, deansOfficeId)){
                return false;
            }
        }
        return true;
    }

    @Override
    public List<ProctoringApplicationDTO> getAllApplicationsByDepartment(int departmentId, boolean isTaInformed) {

        List<ProctoringApplication> listPA = proctoringApplicationRepo.findByVisibleDepartment_DepartmentIdAndIsVisibleForTAs(departmentId, isTaInformed);
        return proctoringApplicationMapper.toDTO(listPA);

    }

    @Override
    public boolean openForTAs(int applicationId) {
        Optional<ProctoringApplication> proctoringApplication = proctoringApplicationRepo.findById(applicationId);
        if(!proctoringApplication.isPresent()){
            throw new RuntimeException("no such proctoring application found");
        }

        ProctoringApplication proctoringApplicationReceieved = proctoringApplication.get();
        proctoringApplicationReceieved.setVisibleForTAs(true);

        proctoringApplicationRepo.save(proctoringApplicationReceieved);
        return true;

    }
}
