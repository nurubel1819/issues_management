package com.example.issues_management.auth.config;


import com.example.issues_management.auth.entity.Role;
import com.example.issues_management.auth.entity.User;
import com.example.issues_management.auth.entity.UserRole;
import com.example.issues_management.auth.repository.RoleRepository;
import com.example.issues_management.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AdminUserInitializer implements CommandLineRunner {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;

	@Value("${app.security.admin.full-name:System Admin}")
	private String adminFullName;

	@Value("${app.security.admin.email:admin@example.com}")
	private String adminEmail;

	@Value("${app.security.admin.password:Admin@12345}")
	private String adminPassword;

	@Override
	public void run(String... args) {
		// Initialize default roles
		initializeRoles();

		// Create admin user if not exists
		if (userRepository.existsByEmail(adminEmail)) {
			return;
		}

		Role adminRole = roleRepository.findByName("ADMIN")
			.orElseThrow(() -> new IllegalStateException("ADMIN role not found"));

		User admin = User.builder()
			.fullName(adminFullName)
			.email(adminEmail)
			.password(passwordEncoder.encode(adminPassword))
			.userRoles(new ArrayList<>())
			.build();

		UserRole userRole = UserRole.builder()
			.user(admin)
			.role(adminRole)
			.build();

		admin.getUserRoles().add(userRole);

		userRepository.save(admin);
	}

	private void initializeRoles() {
		List<String> defaultRoles = List.of("ADMIN", "USER");

		for (String roleName : defaultRoles) {
			if (!roleRepository.existsByName(roleName)) {
				Role role = Role.builder()
					.name(roleName)
					.description("Default " + roleName + " role")
					.build();
				roleRepository.save(role);
			}
		}
	}
}
