package com.cs319group3.backend.Services;

import com.cs319group3.backend.DTOs.AdminProfileDTO;

/**
 * Service interface for operations related to Admin users.
 */
public interface AdminService {

    /**
     * Retrieves the admin profile data for the given admin ID.
     *
     * @param adminId the ID of the admin user
     * @return the profile data for the specified admin
     */
    AdminProfileDTO getAdminProfile(int adminId);

    /**
     * Creates a new admin account using the given user ID and password.
     *
     * @param userId the user ID to be promoted to admin
     * @param password the password for the admin account
     * @return true if the admin was successfully created
     */
    boolean createAdmin(int userId, String password);
}