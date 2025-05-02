package com.cs319group3.backend.Components;
;

import com.cs319group3.backend.Services.ServiceImpls.AuthenticationServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationServiceImpl authServiceImpl;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        // If no Authorization header or doesn't start with "Bearer ", continue filter chain
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        try {
            // Extract the token
            String jwt = authorizationHeader.substring(7);

            // Extract username from token
            String username = jwtUtil.extractUsername(jwt);

            // Check if we have a username and no authentication exists yet
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Load user details
                UserDetails userDetails = authServiceImpl.loadUserByUsername(username);

                // Validate token against username
                if (jwtUtil.validateToken(jwt, username)) {
                    // Create authentication token with user authorities
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

                    // Add request details to authentication
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Set authentication in security context
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                    // Optional: Add user info to request attributes for controllers
                    request.setAttribute("userId", jwtUtil.extractUserId(jwt));
                    request.setAttribute("userTypeId", jwtUtil.extractUserTypeId(jwt));
                    request.setAttribute("email", jwtUtil.extractEmail(jwt));
                }
            }
        } catch (ExpiredJwtException e) {
            logger.error("JWT token has expired: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("JWT token has expired");
            return;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid JWT token");
            return;
        } catch (Exception e) {
            logger.error("Unable to process JWT token: {}", e.getMessage());
        }

        // Continue filter chain
        chain.doFilter(request, response);
    }
}