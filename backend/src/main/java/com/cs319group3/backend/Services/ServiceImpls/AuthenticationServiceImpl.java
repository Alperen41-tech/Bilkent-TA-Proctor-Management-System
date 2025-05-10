package com.cs319group3.backend.Services.ServiceImpls;

import com.cs319group3.backend.DTOs.ChangePasswordDTO;
import com.cs319group3.backend.Entities.Login;
import com.cs319group3.backend.Entities.UserEntities.User;
import com.cs319group3.backend.Enums.LogType;
import com.cs319group3.backend.Repositories.LoginRepo;
import com.cs319group3.backend.Repositories.UserRepo;
import com.cs319group3.backend.Services.AuthenticationService;
import com.cs319group3.backend.Services.EmailService;
import com.cs319group3.backend.Services.LogService;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;
import java.util.Base64;


@Service
public class AuthenticationServiceImpl implements AuthenticationService, UserDetailsService {

    @Autowired
    private LoginRepo loginRepo;
    @Autowired
    private LogService logService;
    @Autowired
    private UserRepo userRepo;
    @Value("${jwt.reset.secret}")
    private String jwtResetSecret;
    @Autowired
    private EmailService emailService;

    @Override
    public boolean changePassword(ChangePasswordDTO changePasswordDTO) {
        Optional<Login> login = loginRepo.findByUser_EmailAndUserType_UserTypeName(
                changePasswordDTO.getEmail(),
                changePasswordDTO.getUserTypeName()
        );

        if (login.isEmpty()) {
            return false;
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (encoder.matches(changePasswordDTO.getOldPassword(), login.get().getPassword())) {
            login.get().setPassword(encoder.encode(changePasswordDTO.getNewPassword()));
            loginRepo.save(login.get());

            String logMessage = "User " + login.get().getUser().getUserId() + " changed password.";
            logService.createLog(logMessage, LogType.UPDATE);

            return true;
        } else {
            System.out.println("Old password does not match");
            return false;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String entrance) throws UsernameNotFoundException {
        String[] parts = entrance.split("::");
        String email = parts[0];
        String userTypeFromFrontend = parts[1];

        Optional<Login> login = loginRepo.findByUser_EmailAndUserType_UserTypeName(email, userTypeFromFrontend);

        if (login.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        Login currLogin = login.get();

        return new org.springframework.security.core.userdetails.User(
                entrance,
                currLogin.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(currLogin.getUserType().getUserTypeName()))
        );
    }

    @Override
    public boolean forgetPassword(String userMail, String userType) {
        Optional<User> userOptional = userRepo.findByEmail(userMail);
        if (!userOptional.isPresent()){
            throw new RuntimeException("User email not found");
        }

        Optional<Login> loginOptional;
        if (!userType.isEmpty() && userType != null){
            loginOptional = loginRepo.findByUser_EmailAndUserType_UserTypeName(userMail, userType);
        }
        else {
            loginOptional = loginRepo.findByUser_Email(userMail);
        }

        if (!loginOptional.isPresent()){
            throw new RuntimeException("User with selected type not found");
        }

        User user = userOptional.get();
        Login login = loginOptional.get();

        // Generate a secure reset token


        byte[] secretKey = Base64.getDecoder().decode(jwtResetSecret);

        String resetToken = Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(Date.from(Instant.now().plus(15, ChronoUnit.MINUTES)))
                .claim("userType", userType)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        String resetLink = "http://localhost:3000/forgot-password?token=" + resetToken;

        // Send the email (use your email service here)
        String subject = "Password Reset Request for Bilkent TA Management Proctoring System";
        String body = "Hi " + user.getName() + ",\n\n"
                + "Please click the following link to reset your password. This link is valid for 30 minutes:\n\n"
                + resetLink + "\n\n"
                + "If you did not request a password reset, please ignore this email.";

        emailService.sendEmail(user.getEmail(), subject, body);

        return true;
    }

    @Override
    public boolean setAfterForgetPassword(String token, String newPassword) {
        try {
            // Decode and validate the JWT token
            byte[] secretKey = Base64.getDecoder().decode(jwtResetSecret);

            // Parse the token to extract claims
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();

            // Extract user information from token
            String userEmail = claims.getSubject();
            String userType = claims.get("userType", String.class);

            // Find the user
            Optional<User> userOptional = userRepo.findByEmail(userEmail);
            if (!userOptional.isPresent()) {
                throw new RuntimeException("User not found");
            }

            // Find the login based on email and userType
            Optional<Login> loginOptional;
            if (userType != null) {
                loginOptional = loginRepo.findByUser_EmailAndUserType_UserTypeName(userEmail, userType);
            } else {
                loginOptional = loginRepo.findByUser_Email(userEmail);
            }

            if (!loginOptional.isPresent()) {
                throw new RuntimeException("Login not found");
            }

            // Update the password
            Login login = loginOptional.get();

            // Encrypt the new password (assuming you have a password encoder)
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(newPassword);
            login.setPassword(encodedPassword);

            // Save the updated login
            loginRepo.save(login);

            return true;
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Password reset link has expired");
        } catch (JwtException e) {
            throw new RuntimeException("Invalid password reset token");
        } catch (Exception e) {
            throw new RuntimeException("Failed to reset password: " + e.getMessage());
        }
    }

}