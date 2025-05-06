package com.cs319group3.backend.Services;

import com.cs319group3.backend.Entities.ClassProctoring;
import com.cs319group3.backend.Entities.UserEntities.TA;

public interface AuthStaffProctoringRequestService {

    public boolean isRequestAlreadySent(int senderId, int receiverId, int classProctoringId);
    public boolean sendAuthStaffProctoringRequest(int classProctoringId, int taId, int senderId, boolean isApproved);
    public boolean canUnforcedRequestBeSent(int classProctoringId);
    public boolean canForcedRequestBeSent(int classProctoringId);
    public boolean canRequestBeAccepted( int classProctoringId);
    public boolean sendAuthStaffProctoringRequestAutomaticallyInDepartment( int classProctoringId, String departmentCode, int senderId, int count, boolean eligibilityRestriction, boolean oneDayRestriction, boolean isApproved);
    public boolean sendAuthStaffProctoringRequestAutomaticallyInFaculty( int classProctoringId, int facultyId, int senderId, int count, boolean eligibilityRestriction, boolean oneDayRestriction, boolean isApproved);
}
