package com.example.issues_management.auth.security;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ApiBasicAuthenticationProvider implements AuthenticationProvider {

	private final PasswordEncoder passwordEncoder;

	@Value("${app.security.api-basic-auth.enabled:false}")
	private boolean enabled;

	@Value("${app.security.api-basic-auth.username:apiuser}")
	private String username;

	@Value("${app.security.api-basic-auth.password:}")
	private String password;

	@Value("${app.security.api-basic-auth.role:ADMIN}")
	private String role;

	private String encodedPassword;

	@PostConstruct
	void initialize() {
		encodedPassword = password.isBlank() ? "" : passwordEncoder.encode(password);
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		if (!enabled) {
			return null;
		}

		String providedUsername = authentication.getName();
		String providedPassword = authentication.getCredentials() == null
			? null
			: authentication.getCredentials().toString();

		boolean usernameMatches = username.equals(providedUsername);
		boolean passwordMatches = providedPassword != null
			&& !encodedPassword.isBlank()
			&& passwordEncoder.matches(providedPassword, encodedPassword);

		if (!usernameMatches || !passwordMatches) {
			throw new BadCredentialsException("Invalid API username or password");
		}

		return new UsernamePasswordAuthenticationToken(
			providedUsername,
			null,
			List.of(new SimpleGrantedAuthority("ROLE_" + role))
		);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
