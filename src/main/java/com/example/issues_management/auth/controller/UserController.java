package com.example.issues_management.auth.controller;

import com.example.issues_management.auth.dto.UserResponse;
import com.example.issues_management.auth.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User Management", description = "APIs for managing and retrieving user information")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

	private final UserService userService;

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	@Operation(summary = "Get user by ID", description = "Retrieves a specific user with their role information. Accessible by ADMIN and USER roles.")
	public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
		UserResponse response = userService.getUserById(id);
		return ResponseEntity.ok(response);
	}

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Get all users", description = "Retrieves all users with their role information. Requires ADMIN role.")
	public ResponseEntity<List<UserResponse>> getAllUsers() {
		List<UserResponse> users = userService.getAllUsers();
		return ResponseEntity.ok(users);
	}
}
