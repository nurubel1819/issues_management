package com.example.issues_management.auth.service;

import com.example.issues_management.auth.dto.RoleResponse;
import com.example.issues_management.auth.dto.UserResponse;
import com.example.issues_management.auth.entity.User;
import com.example.issues_management.auth.repository.UserRepository;
import com.example.issues_management.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	@Transactional(readOnly = true)
	public UserResponse getUserById(Long id) {
		User user = userRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
		return mapToUserResponse(user);
	}

	@Transactional(readOnly = true)
	public List<UserResponse> getAllUsers() {
		return userRepository.findAll().stream()
			.map(this::mapToUserResponse)
			.collect(Collectors.toList());
	}

	private UserResponse mapToUserResponse(User user) {
		List<RoleResponse> roles = user.getUserRoles() != null ?
			user.getUserRoles().stream()
				.map(ur -> RoleResponse.builder()
					.id(ur.getRole().getId())
					.name(ur.getRole().getName())
					.description(ur.getRole().getDescription())
					.createdAt(ur.getRole().getCreatedAt())
					.updatedAt(ur.getRole().getUpdatedAt())
					.build())
				.collect(Collectors.toList()) : List.of();

		return UserResponse.builder()
			.id(user.getId())
			.fullName(user.getFullName())
			.email(user.getEmail())
			.roles(roles)
			.createdAt(user.getCreatedAt())
			.updatedAt(user.getUpdatedAt())
			.build();
	}
}
