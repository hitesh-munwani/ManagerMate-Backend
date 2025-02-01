package com.managermate.backend.dto;

import lombok.Builder;

@Builder
public class LoginResponse {
    private String token;
    private long expiresIn;
}

