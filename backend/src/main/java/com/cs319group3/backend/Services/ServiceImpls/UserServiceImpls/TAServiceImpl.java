package com.cs319group3.backend.Services.ServiceImpls.UserServiceImpls;

import com.cs319group3.backend.DTOMappers.LoginMapper;
import com.cs319group3.backend.DTOs.*;
import com.cs319group3.backend.DTOMappers.TAProfileMapper;
import com.cs319group3.backend.Entities.*;
import com.cs319group3.backend.Entities.UserEntities.TA;
import com.cs319group3.backend.Repositories.*;
import com.cs319group3.backend.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TAServiceImpl implements TAService {

    @Autowired
    private TARepo taRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private LoginRepo loginRepo;

    @Autowired
    private ClassProctoringTARelationRepo classProctoringTARelationRepo;

    @Autowired
    private TAWorkloadRequestService taWorkloadRequestService;


    @Override
    public TAProfileDTO getTAProfileById(int id) {
        Optional<TA> optionalTA = taRepo.findByUserId(id);

        if (optionalTA.isEmpty()) {
            throw new RuntimeException("TA with ID " + id + " not found.");
        }

        return TAProfileMapper.essentialMapper(optionalTA.get());
    }

    @Autowired
    LoginMapper loginMapper;

    @Autowired
    TAProfileMapper taProfileMapper;

    @Override
    public boolean createNewTA(CreateTADTO dto) {
        TAProfileDTO profile = dto.getProfile();
        LoginDTO login = dto.getLogin();

        // Check duplicate by bilkentId or email
        if (userRepo.findByBilkentId(profile.getBilkentId()).isPresent() ||
                userRepo.findByEmail(profile.getEmail()).isPresent()) {
            System.out.println("Duplicate bilkent id or email");
            return false; // Duplicate
        }


        TA ta = taProfileMapper.essentialEntityToTA(profile);
        Login loginEntity = loginMapper.essentialEntityToLogin(login, ta);

        if(ta == null || loginEntity == null) {
            System.out.println("Ta or login cannot be mapped: "+ (ta == null) +", "+(loginEntity == null));
            return false;
        }

        taRepo.save(ta); // saves both into user and ta tables
        loginRepo.save(loginEntity);

        return true;
    }

    @Autowired
    ClassProctoringRepo classProctoringRepo;

    @Autowired
    TAAvailabilityService taAvailabilityService;

    @Override
    public List<TAProfileDTO> getAllAvailableTAsByDepartmentCode(String departmentCode, int classProctoringId, int userId) {
        Optional<ClassProctoring> cpOpt = classProctoringRepo.findById(classProctoringId);
        if(cpOpt.isEmpty()) {
            System.out.println("Cannot fetch available TAs because class proctoring not found.");
            return null;
        }
        ClassProctoring cp = cpOpt.get();
        //Fetches all TAs in a department currently not in the given proctoring
        List<TA> availableTAs = taRepo.findAvailableTAsByDepartment(departmentCode, classProctoringId);
        int courseId = cp.getCourse().getCourseId();

        availableTAs.removeIf(ta -> {
            boolean unavailable = !taAvailabilityService.isTAAvailable(ta, cp);
            boolean alreadyRequested = authStaffProctoringRequestService.isRequestAlreadySent(userId, ta.getUserId(), classProctoringId);
            boolean takesSameCourse = doesTakeCourse(ta.getUserId(), courseId);

            return unavailable || alreadyRequested || takesSameCourse;
        });

        List<TAProfileDTO> availableTAProfiles = new ArrayList<>();
        for (TA ta : availableTAs) {
            TAProfileDTO profile = TAProfileMapper.essentialMapper(ta);
            profile.setTAOfTheCourse(ta.getAssignedCourse().getCourseId() == courseId);
            availableTAProfiles.add(profile);
        }
        return availableTAProfiles;
    }

    @Override
    public List<TAProfileDTO> getAllAvailableTAsByDepartmentCode(String departmentCode, int classProctoringId, int userId, boolean eligibilityRestriction, boolean oneDayRestriction) {
        ClassProctoring cp = classProctoringRepo.findById(classProctoringId).get();
        List<TA> availableTAs = taRepo.findAvailableTAsByDepartment(departmentCode, classProctoringId);
        int courseId = cp.getCourse().getCourseId();

        availableTAs.removeIf(ta -> {
            boolean unavailable = !taAvailabilityService.isTAAvailable(ta, cp);
            boolean alreadyRequested = authStaffProctoringRequestService.isRequestAlreadySent(userId, ta.getUserId(), classProctoringId);
            boolean takesSameCourse = doesTakeCourse(ta.getUserId(), courseId);
            boolean ineligible = eligibilityRestriction && !isTAEligible(ta.getUserId(), courseId);
            boolean failsOneDayRule = oneDayRestriction && !noProctoringInOneDay(ta.getUserId(), classProctoringId);

            return unavailable || alreadyRequested || takesSameCourse || ineligible || failsOneDayRule;
        });

        List<TAProfileDTO> availableTAProfiles = new ArrayList<>();
        for (TA ta : availableTAs) {
            TAProfileDTO profile = TAProfileMapper.essentialMapper(ta);
            profile.setTAOfTheCourse(ta.getAssignedCourse().getCourseId() == courseId);
            availableTAProfiles.add(profile);
        }
        return availableTAProfiles;
    }

    @Autowired
    AuthStaffProctoringRequestService authStaffProctoringRequestService;

    @Override
    public List<TAProfileDTO> getAllAvailableTAsByFacultyId(int facultyId, int classProctoringId, int userId) {
        ClassProctoring cp = classProctoringRepo.findById(classProctoringId).get();
        List<TA> availableTAs = taRepo.findAvailableTAsByFaculty(facultyId, classProctoringId);
        int courseId = cp.getCourse().getCourseId();

        availableTAs.removeIf(ta -> {
            boolean unavailable = !taAvailabilityService.isTAAvailable(ta, cp);
            System.out.println("1. ta with id "+ta.getUserId()+ "is" + unavailable);
            boolean alreadyRequested = authStaffProctoringRequestService.isRequestAlreadySent(userId, ta.getUserId(), classProctoringId);
            System.out.println("2. ta with id "+ta.getUserId()+ "is" + alreadyRequested);
            boolean takesSameCourse = doesTakeCourse(ta.getUserId(), courseId);
            System.out.println("3. ta with id "+ta.getUserId()+ "is" + takesSameCourse);

            return unavailable || alreadyRequested || takesSameCourse;
        });

        List<TAProfileDTO> availableTAProfiles = new ArrayList<>();
        for (TA ta : availableTAs) {
            TAProfileDTO profile = TAProfileMapper.essentialMapper(ta);
            profile.setTAOfTheCourse(ta.getAssignedCourse().getCourseId() == courseId);
            availableTAProfiles.add(profile);
        }

        return availableTAProfiles;
    }

    @Override
    public List<TAProfileDTO> getAllAvailableTAsByFacultyId(int facultyId, int classProctoringId, int userId, boolean eligibilityRestriction, boolean oneDayRestriction) {
        ClassProctoring cp = classProctoringRepo.findById(classProctoringId).get();
        List<TA> availableTAs = taRepo.findAvailableTAsByFaculty(facultyId, classProctoringId);
        int courseId = cp.getCourse().getCourseId();

        availableTAs.removeIf(ta -> {
            boolean unavailable = !taAvailabilityService.isTAAvailable(ta, cp);
            boolean alreadyRequested = authStaffProctoringRequestService.isRequestAlreadySent(userId, ta.getUserId(), classProctoringId);
            boolean takesSameCourse = doesTakeCourse(ta.getUserId(), courseId);
            boolean ineligible = eligibilityRestriction && !isTAEligible(ta.getUserId(), courseId);
            boolean failsOneDayRule = oneDayRestriction && !noProctoringInOneDay(ta.getUserId(), classProctoringId);

            return unavailable || alreadyRequested || takesSameCourse || ineligible || failsOneDayRule;
        });

        List<TAProfileDTO> availableTAProfiles = new ArrayList<>();
        for (TA ta : availableTAs) {
            TAProfileDTO profile = TAProfileMapper.essentialMapper(ta);
            profile.setTAOfTheCourse(ta.getAssignedCourse().getCourseId() == courseId);
            availableTAProfiles.add(profile);
        }

        return availableTAProfiles;
    }

    @Override
    public List<TAProfileDTO> getAllTAProfiles() {
        List<TA> tas = taRepo.findAll();
        List<TAProfileDTO> TAProfiles = new ArrayList<>();
        for (TA ta : tas) {
            TAProfileDTO dto = TAProfileMapper.essentialMapper(ta);
            dto.setWorkload(ta.getWorkload());
            TAProfiles.add(dto);
        }
        return TAProfiles;
    }

    @Autowired
    CourseRepo courseRepo;

    @Override
    public boolean doesTakeCourse(int taId, int courseId) {
        List<Integer> listOfCourseIds = courseRepo.findAllCourseIdsByUserId(taId);
        return listOfCourseIds.contains(courseId);
    }

    @Autowired
    StudentRepo studentRepo;

    @Override
    public boolean isTAEligible(int taId, int courseId) {
        Optional<Integer> courseCode = courseRepo.findCourseCodeByCourseId(courseId);
        if(courseCode.isEmpty()) {
            System.out.println("Course code cannot be found");
            return false;
        }
        int code = courseCode.get();
        if(code / 100 > 4){
            Optional<Integer> classYear = studentRepo.findClassById(taId);
            if(classYear.isEmpty()) {
                System.out.println("Class year cannot be found");
                return false;
            }

            return classYear.get() == 9;
        }
        return true;
    }

    @Autowired
    TAAvailabilityService taaAvailabilityService;

    @Override
    public boolean noProctoringInOneDay(int taId, int proctoringId) {
        Optional<ClassProctoring> cpOptional = classProctoringRepo.findByClassProctoringId(proctoringId);
        if(cpOptional.isEmpty()) {
            System.out.println("Class Proctoring not found");
            return false;
        }
        ClassProctoring classProctoring = cpOptional.get();
        LocalDateTime startTime = classProctoring.getStartDate().minusDays(1);
        LocalDateTime endTime = classProctoring.getEndDate().plusDays(1);
        Optional<TA> ta = taRepo.findById(taId);
        if(ta.isEmpty()) {
            System.out.println("Ta cannot be found");
            return false;
        }

        return !taaAvailabilityService.hasAnotherProctoring(ta.get(), startTime, endTime);
    }
}