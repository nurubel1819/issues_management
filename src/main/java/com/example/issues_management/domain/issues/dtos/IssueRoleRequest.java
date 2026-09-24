package com.example.issues_management.domain.issues.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class IssueRoleRequest {
    @NotBlank
    private String name;
    private String description;
}