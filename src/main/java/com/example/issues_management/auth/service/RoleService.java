package com.example.issues_management.auth.service;

import com.example.issues_management.auth.dto.AssignRoleRequest;
import com.example.issues_management.auth.dto.RoleRequest;
import com.example.issues_management.auth.dto.RoleResponse;
import com.example.issues_management.auth.entity.Role;
import com.example.issues_management.auth.entity.User;
import com.example.issues_management.auth.entity.UserRole;
import com.example.issues_management.auth.repository.RoleRepository;
import com.example.issues_management.auth.repository.UserRepository;
import com.example.issues_management.auth.repository.UserRoleRepository;
import com.example.issues_management.common.exception.ResourceNotFoundException;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {

	private final RoleRepository roleRepository;
	private final UserRepository userRepository;
	private final UserRoleRepository userRoleRepository;
	private final EntityManager entityManager;

	@Transactional
	public RoleResponse createRole(RoleRequest request) {
		if (roleRepository.existsByName(request.getName())) {
			throw new IllegalArgumentException("Role with name '" + request.getName() + "' already exists");
		}

		Role role = Role.builder()
			.name(request.getName())
			.description(request.getDescription())
			.build();

		Role savedRole = roleRepository.save(role);
		return mapToResponse(savedRole);
	}

	@Transactional(readOnly = true)
	public List<RoleResponse> getAllRoles() {
		return roleRepository.findAll().stream()
			.map(this::mapToResponse)
			.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public RoleResponse getRoleById(Long id) {
		Role role = roleRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));
		return mapToResponse(role);
	}

	@Transactional
	public RoleResponse updateRole(Long id, RoleRequest request) {
		Role role = roleRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));

		// Check if the new name already exists (excluding current role)
		if (!role.getName().equals(request.getName()) && roleRepository.existsByName(request.getName())) {
			throw new IllegalArgumentException("Role with name '" + request.getName() + "' already exists");
		}

		role.setName(request.getName());
		role.setDescription(request.getDescription());

		Role updatedRole = roleRepository.save(role);
		return mapToResponse(updatedRole);
	}

	@Transactional
	public void deleteRole(Long id) {
		Role role = roleRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));

		// Check if role is assigned to any users
		List<UserRole> userRoles = userRoleRepository.findByUserId(id);
		if (!userRoles.isEmpty()) {
			throw new IllegalStateException("Cannot delete role that is assigned to users");
		}

		roleRepository.delete(role);
	}

	@Transactional
	public void assignRolesToUser(AssignRoleRequest request) {
		// Verify user exists
		User user = userRepository.findById(request.getUserId())
			.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + request.getUserId()));

		// Delete existing roles using JPQL query
		userRoleRepository.deleteByUserId(request.getUserId());

		// Flush to ensure deletes are executed
		entityManager.flush();

		// Detach user to avoid merge issues
		entityManager.detach(user);

		// Reload user with fresh state
		user = userRepository.findById(request.getUserId())
			.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + request.getUserId()));

		// Initialize collection if needed
		if (user.getUserRoles() == null) {
			user.setUserRoles(new ArrayList<>());
		}

		// Assign new roles
		for (Long roleId : request.getRoleIds()) {
			Role role = roleRepository.findById(roleId)
				.orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + roleId));

			UserRole userRole = UserRole.builder()
				.user(user)
				.role(role)
				.build();

			user.getUserRoles().add(userRole);
		}

		userRepository.save(user);
	}

	@Transactional
	public void removeRoleFromUser(Long userId, Long roleId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

		UserRole userRole = userRoleRepository.findByUserIdAndRoleId(userId, roleId)
			.orElseThrow(() -> new ResourceNotFoundException("User role assignment not found"));

		user.getUserRoles().remove(userRole);
		userRoleRepository.delete(userRole);
	}

	@Transactional(readOnly = true)
	public List<RoleResponse> getUserRoles(Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

		return user.getUserRoles().stream()
			.map(userRole -> mapToResponse(userRole.getRole()))
			.collect(Collectors.toList());
	}

	private RoleResponse mapToResponse(Role role) {
		return RoleResponse.builder()
			.id(role.getId())
			.name(role.getName())
			.description(role.getDescription())
			.createdAt(role.getCreatedAt())
			.updatedAt(role.getUpdatedAt())
			.build();
	}
}
