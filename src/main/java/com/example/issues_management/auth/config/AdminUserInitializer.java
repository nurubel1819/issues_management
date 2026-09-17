package com.example.issues_management.auth.config;


import com.example.issues_management.auth.entity.Role;
import com.example.issues_management.auth.entity.User;
import com.example.issues_management.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminUserInitializer implements CommandLineRunner {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Value("${app.security.admin.full-name:System Admin}")
	private String adminFullName;

	@Value("${app.security.admin.email:admin@example.com}")
	private String adminEmail;

	@Value("${app.security.admin.password:Admin@12345}")
	private String adminPassword;

	@Override
	public void run(String... args) {
		if (userRepository.existsByEmail(adminEmail)) {
			return;
		}

		User admin = User.builder()
			.fullName(adminFullName)
			.email(adminEmail)
			.password(passwordEncoder.encode(adminPassword))
			.role(Role.ADMIN)
			.build();

		userRepository.save(admin);
	}
}
