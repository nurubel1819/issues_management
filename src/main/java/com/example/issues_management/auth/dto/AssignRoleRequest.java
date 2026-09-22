package com.example.issues_management.auth.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignRoleRequest {

	@NotNull(message = "User ID is required")
	private Long userId;

	@NotNull(message = "Role IDs are required")
	private List<Long> roleIds;
}
