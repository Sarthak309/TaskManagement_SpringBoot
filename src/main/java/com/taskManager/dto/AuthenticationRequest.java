package com.taskManager.dto;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.Data;

@Data
public class AuthenticationRequest {

    private String email;
    private String password;

    }
