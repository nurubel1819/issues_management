package com.example.issues_management.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
	@NotBlank(message = "Email is required")
	@Email(message = "Email must be valid")
	@Schema(example = "admin@example.com")
	String email,

	@NotBlank(message = "Password is required")
	@Schema(example = "Admin@12345")
	String password
) {
}
