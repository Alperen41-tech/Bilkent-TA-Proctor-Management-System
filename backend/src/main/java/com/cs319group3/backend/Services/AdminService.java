package com.cs319group3.backend.Services;

import com.cs319group3.backend.DTOs.AdminProfileDTO;

public interface AdminService {
    public AdminProfileDTO getAdminProfile(int adminId);
}
