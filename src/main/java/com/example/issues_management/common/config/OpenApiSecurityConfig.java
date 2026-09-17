package com.example.issues_management.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiSecurityConfig {

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
			.components(new Components()
				.addSecuritySchemes(
					"bearerAuth",
					new SecurityScheme()
						.type(SecurityScheme.Type.HTTP)
						.scheme("bearer")
						.bearerFormat("JWT")
						.description("JWT bearer token. Example: eyJhbGciOi...")
				)
				.addSecuritySchemes(
					"basicAuth",
					new SecurityScheme()
						.type(SecurityScheme.Type.HTTP)
						.scheme("basic")
						.description("Username/password API access")
				)
			)
			// Separate requirement entries mean OR semantics (Bearer OR Basic).
			.addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
			.addSecurityItem(new SecurityRequirement().addList("basicAuth"));
	}
}
