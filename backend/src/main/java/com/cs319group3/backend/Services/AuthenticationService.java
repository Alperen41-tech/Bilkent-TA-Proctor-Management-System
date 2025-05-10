package com.cs319group3.backend.Services;

import com.cs319group3.backend.DTOs.ChangePasswordDTO;
import com.cs319group3.backend.DTOs.LoginDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface AuthenticationService {

    /**
     * Changes the password of a user.
     *
     * @param newLoginDTO contains the current password and the new password
     * @return true if the password change was successful, false otherwise
     */
    boolean changePassword(ChangePasswordDTO newLoginDTO);

    /**
     * Loads user details by a combined username string (e.g., email::role).
     *
     * @param entrance the combined username string
     * @return UserDetails containing user information for Spring Security
     * @throws UsernameNotFoundException if the user is not found
     */
    UserDetails loadUserByUsername(String entrance) throws UsernameNotFoundException;

    /**
     * when users forget their password, they can get new one
     *
     * @param userMail the current user mail
     * @param userType is the user admim
     * @return boolean according to success of getting new password;
     */
    boolean forgetPassword(String userMail, String userType);
}