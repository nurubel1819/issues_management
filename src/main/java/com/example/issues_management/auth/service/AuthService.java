package com.example.issues_management.auth.service;


import com.example.issues_management.auth.dto.AuthResponse;
import com.example.issues_management.auth.dto.LoginRequest;
import com.example.issues_management.auth.dto.RegisterRequest;

public interface AuthService {
	AuthResponse register(RegisterRequest request);

	AuthResponse login(LoginRequest request);
}
