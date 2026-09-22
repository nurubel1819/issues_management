package com.example.issues_management.auth.controller;

import com.example.issues_management.auth.dto.AssignRoleRequest;
import com.example.issues_management.auth.dto.RoleRequest;
import com.example.issues_management.auth.dto.RoleResponse;
import com.example.issues_management.auth.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@Tag(name = "Role Management", description = "APIs for managing roles and user role assignments")
@SecurityRequirement(name = "bearerAuth")
public class RoleController {

	private final RoleService roleService;

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Create a new role", description = "Creates a new role. Requires ADMIN role.")
	public ResponseEntity<RoleResponse> createRole(@Valid @RequestBody RoleRequest request) {
		RoleResponse response = roleService.createRole(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Get all roles", description = "Retrieves all roles. Requires ADMIN role.")
	public ResponseEntity<List<RoleResponse>> getAllRoles() {
		List<RoleResponse> roles = roleService.getAllRoles();
		return ResponseEntity.ok(roles);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Get role by ID", description = "Retrieves a role by its ID. Requires ADMIN role.")
	public ResponseEntity<RoleResponse> getRoleById(@PathVariable Long id) {
		RoleResponse response = roleService.getRoleById(id);
		return ResponseEntity.ok(response);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Update a role", description = "Updates an existing role. Requires ADMIN role.")
	public ResponseEntity<RoleResponse> updateRole(@PathVariable Long id, @Valid @RequestBody RoleRequest request) {
		RoleResponse response = roleService.updateRole(id, request);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Delete a role", description = "Deletes a role. Requires ADMIN role.")
	public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
		roleService.deleteRole(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/assign")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Assign roles to user", description = "Assigns multiple roles to a user. Requires ADMIN role.")
	public ResponseEntity<Void> assignRolesToUser(@Valid @RequestBody AssignRoleRequest request) {
		roleService.assignRolesToUser(request);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/users/{userId}/roles/{roleId}")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Remove role from user", description = "Removes a specific role from a user. Requires ADMIN role.")
	public ResponseEntity<Void> removeRoleFromUser(@PathVariable Long userId, @PathVariable Long roleId) {
		roleService.removeRoleFromUser(userId, roleId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/users/{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "Get user's roles", description = "Retrieves all roles assigned to a user. Requires ADMIN role.")
	public ResponseEntity<List<RoleResponse>> getUserRoles(@PathVariable Long userId) {
		List<RoleResponse> roles = roleService.getUserRoles(userId);
		return ResponseEntity.ok(roles);
	}
}
