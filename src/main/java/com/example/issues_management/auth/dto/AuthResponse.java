package com.example.issues_management.auth.dto;


import java.util.List;

public record AuthResponse(
	String accessToken,
	String tokenType,
	long expiresIn,
	String email,
	List<String> roles
) {
}
