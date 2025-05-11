package com.cs319group3.backend.Services.ServiceImpls.UserServiceImpls;

import com.cs319group3.backend.Components.CurrentUserUtil;
import com.cs319group3.backend.DTOMappers.LoginMapper;
import com.cs319group3.backend.DTOMappers.TAProfileMapper;
import com.cs319group3.backend.DTOs.*;
import com.cs319group3.backend.Entities.ClassProctoring;
import com.cs319group3.backend.Entities.Department;
import com.cs319group3.backend.Entities.Login;
import com.cs319group3.backend.Entities.UserEntities.DepartmentSecretary;
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

    @Autowired
    private CurrentUserUtil currentUserUtil;

    @Autowired
    private TAProfileMapper taProfileMapper;

    @Autowired
    private LoginMapper loginMapper;

    @Autowired
    private ClassProctoringRepo classProctoringRepo;

    @Autowired
    private TAAvailabilityService taAvailabilityService;

    @Autowired
    private AuthStaffProctoringRequestService authStaffProctoringRequestService;

    @Autowired
    private CourseRepo courseRepo;

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private TAAvailabilityService taaAvailabilityService;
    @Autowired
    private DepartmentSecretaryRepo departmentSecretaryRepo;

    @Override
    public TAProfileDTO getTAProfileById(int id) {
        Optional<TA> optionalTA = taRepo.findByUserId(id);
        if (optionalTA.isEmpty()) {
            throw new RuntimeException("TA with ID " + id + " not found.");
        }
        return taProfileMapper.essentialMapper(optionalTA.get());
    }

    @Override
    public boolean createNewTA(CreateTADTO dto) {
        TAProfileDTO profile = dto.getProfile();
        LoginDTO login = dto.getLogin();

        if (userRepo.findByBilkentId(profile.getBilkentId()).isPresent() ||
                userRepo.findByEmail(profile.getEmail()).isPresent()) {
            System.out.println("Duplicate bilkent id or email");
            return false;
        }

        TA ta = taProfileMapper.essentialEntityToTA(profile);
        Login loginEntity = loginMapper.essentialEntityToLogin(login, ta);

        if (ta == null || loginEntity == null) {
            System.out.println("Ta or login cannot be mapped: " + (ta == null) + ", " + (loginEntity == null));
            return false;
        }

        taRepo.save(ta);
        loginRepo.save(loginEntity);
        return true;
    }

    @Override
    public List<TAProfileDTO> getAllAvailableTAsByDepartmentCode(String departmentCode, int classProctoringId, int userId) {
        Optional<ClassProctoring> cpOpt = classProctoringRepo.findById(classProctoringId);
        if (cpOpt.isEmpty()) {
            System.out.println("Cannot fetch available TAs because class proctoring not found.");
            return null;
        }

        ClassProctoring cp = cpOpt.get();
        List<TA> availableTAs = taRepo.findAvailableTAsByDepartment(departmentCode, classProctoringId);
        int courseId = cp.getCourse().getCourseId();

        availableTAs.removeIf(ta -> {
            boolean unavailable = !taAvailabilityService.isTAAvailable(ta, cp);
            boolean alreadyRequested = authStaffProctoringRequestService.isRequestAlreadySent(ta.getUserId(), classProctoringId);
            boolean takesSameCourse = doesTakeCourse(ta.getUserId(), courseId);
            System.out.println("TA with id " + ta.getUserId() + ": " + unavailable + ", " + alreadyRequested + ", " + takesSameCourse);
            return unavailable || alreadyRequested || takesSameCourse;
        });

        List<TAProfileDTO> availableTAProfiles = new ArrayList<>();
        for (TA ta : availableTAs) {
            TAProfileDTO profile = taProfileMapper.essentialMapper(ta);
            profile.setTAOfTheCourse(ta.getAssignedCourse().getCourseId() == courseId);
            availableTAProfiles.add(profile);
        }
        return availableTAProfiles;
    }

    @Override
    public List<TAProfileDTO> getAllAvailableTAsByDepartmentCode(String departmentCode, int classProctoringId, int userId,
                                                                 boolean eligibilityRestriction, boolean oneDayRestriction) {
        ClassProctoring cp = classProctoringRepo.findById(classProctoringId).get();
        List<TA> availableTAs = taRepo.findAvailableTAsByDepartment(departmentCode, classProctoringId);
        for(TA ta : availableTAs){
            System.out.println("TA with id " + ta.getUserId() + ": " + ta.getAssignedCourse().getCourseName());
        }
        int courseId = cp.getCourse().getCourseId();

        System.out.println("Number of available TAs: " + availableTAs.size());

        availableTAs.removeIf(ta -> {
            boolean unavailable = !taAvailabilityService.isTAAvailable(ta, cp);
            boolean alreadyRequested = authStaffProctoringRequestService.isRequestAlreadySent(ta.getUserId(), classProctoringId);
            boolean takesSameCourse = doesTakeCourse(ta.getUserId(), courseId);
            boolean ineligible = eligibilityRestriction && !isTAEligible(ta.getUserId(), courseId);
            boolean failsOneDayRule = oneDayRestriction && !noProctoringInOneDay(ta.getUserId(), classProctoringId);
            System.out.println(unavailable + ", " + alreadyRequested + ", " + takesSameCourse + ", " + ineligible + ", " + failsOneDayRule);
            return unavailable || alreadyRequested || takesSameCourse || ineligible || failsOneDayRule;
        });

        List<TAProfileDTO> availableTAProfiles = new ArrayList<>();
        for (TA ta : availableTAs) {
            TAProfileDTO profile = taProfileMapper.essentialMapper(ta);
            profile.setTAOfTheCourse(ta.getAssignedCourse().getCourseId() == courseId);
            availableTAProfiles.add(profile);
        }
        return availableTAProfiles;
    }

    @Override
    public List<TAProfileDTO> getAllAvailableTAsByFacultyId(int facultyId, int classProctoringId, int userId) {
        ClassProctoring cp = classProctoringRepo.findById(classProctoringId).get();
        List<TA> availableTAs = taRepo.findAvailableTAsByFaculty(facultyId, classProctoringId);
        int courseId = cp.getCourse().getCourseId();

        availableTAs.removeIf(ta -> {
            boolean unavailable = !taAvailabilityService.isTAAvailable(ta, cp);
            boolean alreadyRequested = authStaffProctoringRequestService.isRequestAlreadySent(ta.getUserId(), classProctoringId);
            boolean takesSameCourse = doesTakeCourse(ta.getUserId(), courseId);
            System.out.println("TA with id " + ta.getUserId() + ": " + unavailable + ", " + alreadyRequested + ", " + takesSameCourse);
            return unavailable || alreadyRequested || takesSameCourse;
        });

        List<TAProfileDTO> availableTAProfiles = new ArrayList<>();
        for (TA ta : availableTAs) {
            TAProfileDTO profile = taProfileMapper.essentialMapper(ta);
            profile.setTAOfTheCourse(ta.getAssignedCourse().getCourseId() == courseId);
            availableTAProfiles.add(profile);
        }
        return availableTAProfiles;
    }

    @Override
    public List<TAProfileDTO> getAllAvailableTAsByFacultyId(int facultyId, int classProctoringId, int userId,
                                                            boolean eligibilityRestriction, boolean oneDayRestriction) {
        ClassProctoring cp = classProctoringRepo.findById(classProctoringId).get();
        List<TA> availableTAs = taRepo.findAvailableTAsByFaculty(facultyId, classProctoringId);
        int courseId = cp.getCourse().getCourseId();

        availableTAs.removeIf(ta -> {
            boolean unavailable = !taAvailabilityService.isTAAvailable(ta, cp);
            boolean alreadyRequested = authStaffProctoringRequestService.isRequestAlreadySent(ta.getUserId(), classProctoringId);
            boolean takesSameCourse = doesTakeCourse(ta.getUserId(), courseId);
            boolean ineligible = eligibilityRestriction && !isTAEligible(ta.getUserId(), courseId);
            boolean failsOneDayRule = oneDayRestriction && !noProctoringInOneDay(ta.getUserId(), classProctoringId);
            return unavailable || alreadyRequested || takesSameCourse || ineligible || failsOneDayRule;
        });

        List<TAProfileDTO> availableTAProfiles = new ArrayList<>();
        for (TA ta : availableTAs) {
            TAProfileDTO profile = taProfileMapper.essentialMapper(ta);
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
            TAProfileDTO dto = taProfileMapper.essentialMapper(ta);
            dto.setWorkload(ta.getWorkload());
            TAProfiles.add(dto);
        }
        return TAProfiles;
    }

    @Override
    public boolean doesTakeCourse(int taId, int courseId) {
        List<Integer> listOfCourseIds = courseRepo.findAllCourseIdsByUserId(taId);
        return listOfCourseIds.contains(courseId);
    }

    @Override
    public boolean isTAEligible(int taId, int courseId) {
        Optional<Integer> courseCode = courseRepo.findCourseCodeByCourseId(courseId);
        if (courseCode.isEmpty()) {
            System.out.println("Course code cannot be found");
            return false;
        }

        int code = courseCode.get();
        if (code / 100 > 4) {
            Optional<Integer> classYear = studentRepo.findClassById(taId);
            if (classYear.isEmpty()) {
                System.out.println("Class year cannot be found");
                return false;
            }
            return classYear.get() == 9;
        }
        return true;
    }

    @Override
    public boolean noProctoringInOneDay(int taId, int proctoringId) {
        Optional<ClassProctoring> cpOptional = classProctoringRepo.findByClassProctoringId(proctoringId);
        if (cpOptional.isEmpty()) {
            System.out.println("Class Proctoring not found");
            return false;
        }

        ClassProctoring classProctoring = cpOptional.get();
        LocalDateTime startTime = classProctoring.getStartDate().minusDays(1);
        LocalDateTime endTime = classProctoring.getEndDate().plusDays(1);
        Optional<TA> ta = taRepo.findById(taId);

        if (ta.isEmpty()) {
            System.out.println("TA cannot be found");
            return false;
        }

        return !taaAvailabilityService.hasAnotherProctoring(ta.get(), startTime, endTime);
    }
}