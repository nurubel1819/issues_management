package com.example.issues_management.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
	private Long id;
	private String fullName;
	private String email;
	private List<RoleResponse> roles;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
