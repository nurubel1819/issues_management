package com.example.issues_management.auth.dto;


import com.example.issues_management.auth.entity.Role;

public record AuthResponse(
	String accessToken,
	String tokenType,
	long expiresIn,
	String email,
	Role role
) {
}
