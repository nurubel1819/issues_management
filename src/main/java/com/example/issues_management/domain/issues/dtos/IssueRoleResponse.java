package com.example.issues_management.domain.issues.dtos;

import lombok.*;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class IssueRoleResponse {
    private Long id;
    private String name;
    private String description;
}