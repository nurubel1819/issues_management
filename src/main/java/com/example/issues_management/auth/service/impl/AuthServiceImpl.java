package com.example.issues_management.auth.service.impl;


import com.example.issues_management.auth.dto.AuthResponse;
import com.example.issues_management.auth.dto.LoginRequest;
import com.example.issues_management.auth.dto.RegisterRequest;
import com.example.issues_management.auth.entity.Role;
import com.example.issues_management.auth.entity.User;
import com.example.issues_management.auth.repository.UserRepository;
import com.example.issues_management.auth.security.JwtService;
import com.example.issues_management.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	@Override
	@Transactional
	public AuthResponse register(RegisterRequest request) {
		if (userRepository.existsByEmail(request.email())) {
			throw new IllegalArgumentException("Email is already in use");
		}

		User user = User.builder()
			.fullName(request.fullName())
			.email(request.email())
			.password(passwordEncoder.encode(request.password()))
			.build();

		User savedUser = userRepository.save(user);

		List<String> roleNames = savedUser.getUserRoles() != null ?
			savedUser.getUserRoles().stream()
				.map(ur -> ur.getRole().getName())
				.toList() : List.of();

		String jwtToken = jwtService.generateToken(savedUser, Map.of("roles", roleNames));

		return new AuthResponse(
			jwtToken,
			"Bearer",
			jwtService.getExpirationMs(),
			savedUser.getEmail(),
			roleNames
		);
	}

	@Override
	public AuthResponse login(LoginRequest request) {
		try {
			authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.email(), request.password())
			);
		} catch (AuthenticationException exception) {
			throw new IllegalArgumentException("Invalid email or password");
		}

		User user = userRepository.findByEmail(request.email())
			.orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

		List<String> roleNames = user.getUserRoles() != null ?
			user.getUserRoles().stream()
				.map(ur -> ur.getRole().getName())
				.toList() : List.of();

		String jwtToken = jwtService.generateToken(user, Map.of("roles", roleNames));
		return new AuthResponse(
			jwtToken,
			"Bearer",
			jwtService.getExpirationMs(),
			user.getEmail(),
			roleNames
		);
	}
}
