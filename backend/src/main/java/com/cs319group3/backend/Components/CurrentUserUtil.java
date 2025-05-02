package com.cs319group3.backend.Components;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Utility class to easily access the current authenticated user's information
 */
@Component
public class CurrentUserUtil {

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Get the username of the currently authenticated user
     * @return the username or null if not authenticated
     */
    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }

        return principal.toString();
    }

    /**
     * Get the email from the combined username (email::userType)
     * @return the email or null if not found
     */
    public String getCurrentEmail() {
        String username = getCurrentUsername();
        if (username == null) {
            return null;
        }

        String[] parts = username.split("::");
        return parts.length > 0 ? parts[0] : null;
    }

    /**
     * Get the user type name from the combined username (email::userType)
     * @return the user type name or null if not found
     */
    public String getCurrentUserTypeName() {
        String username = getCurrentUsername();
        if (username == null) {
            return null;
        }

        String[] parts = username.split("::");
        return parts.length > 1 ? parts[1] : null;
    }

    public int getCurrentUserId() {
        String token = getJwtTokenFromRequest();
        if (token == null) {
            return 0;
        }
        return jwtUtil.extractUserId(token);
    }

    public int getCurrentUserTypeId() {
        String token = getJwtTokenFromRequest();
        if (token == null) {
            return 0;
        }
        return jwtUtil.extractUserTypeId(token);
    }

    private String getJwtTokenFromRequest() {
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes == null) {
            return null;
        }

        HttpServletRequest request = attributes.getRequest();
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        return null;
    }


    /**
     * Check if the current user has a specific authority/role
     * @param authority the authority to check
     * @return true if the user has the authority, false otherwise
     */
    public boolean hasAuthority(String authority) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        return authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(authority));
    }
}