package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig
{

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      http
        .csrf(csrf -> csrf.disable()) // disable CSRF for simplicity
        .authorizeHttpRequests(auth -> auth
            // Public pages: customer login, registration, static resources
            .requestMatchers(
                "/customer/**",       
                "/admin/**",
                "/customer/**",
                "/order/**",
                "/css/**",
                "/js/**",
                "/image/**",
                "/Upload/**"
                
            ).permitAll()   
        
        );


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() 
    {
        // Use BCrypt for password hashing
        return new BCryptPasswordEncoder();
    }
}