package com.cs319group3.backend.Configurations;


import com.cs319group3.backend.Services.ServiceImpls.LoginServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // ❗ CSRF must be disabled for non-browser clients like Postman
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/api/**").permitAll()
                        .anyRequest().authenticated()
                )
                .logout(logout -> logout.permitAll());
        return http.build();
    }


    @Bean
    public AuthenticationProvider authenticationProvider(LoginServiceImpl loginServiceImpl) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(loginServiceImpl);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



}
