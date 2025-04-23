package com.cs319group3.backend.Components;

import com.cs319group3.backend.Services.ServiceImpls.LoginServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private LoginServiceImpl loginServiceImpl;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);  // Extract the token
            username = jwtUtil.extractUsername(jwt);  // Extract the username from the token
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Load the user details from the database using the username
            UserDetails userDetails = loginServiceImpl.loadUserByUsername(username);

            if (jwtUtil.validateToken(jwt)) {
                // Extract additional claims from the token
                String email = jwtUtil.extractEmail(jwt);
                String userType = jwtUtil.extractUserType(jwt);
                String role = jwtUtil.extractRole(jwt);

                String password = userDetails.getPassword() != null ? userDetails.getPassword() : "";


                // Create CustomUserDetails with the extracted information
                userDetails = new CustomUserDetails(
                        username,  // username extracted from JWT
                        userDetails.getPassword(), // password (could be fetched from DB)
                        userType,  // userType extracted from JWT
                        userDetails.getUsername().hashCode(),  // Example userId (use actual value if needed)
                        "",        // bilkentId (could be added if available in JWT)
                        userDetails.getUsername(), // Use username as name placeholder
                        "",        // surname placeholder
                        email,     // email extracted from JWT
                        "",        // phoneNumber placeholder
                        userDetails.getAuthorities() // Pass authorities
                );

                // Create an authentication token
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                // Set details for the request
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set the authentication in the security context
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Continue the filter chain
        chain.doFilter(request, response);
    }
}
