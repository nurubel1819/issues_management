package com.example.issues_management.domain.teams.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class TeamRequest {
    @NotBlank
    private String name;
    private String description;
}
