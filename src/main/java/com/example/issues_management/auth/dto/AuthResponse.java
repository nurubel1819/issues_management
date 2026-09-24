package com.example.issues_management.auth.dto;

import java.util.List;

public record AuthResponse(
        Long userId,
        String accessToken,
        String refreshToken,
        String tokenType,
        long expiresIn,
        String email,
        List<String> roles
) {
}