package com.example.issues_management.domain.issues.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class AssignForTeamRequest {
    @NotNull
    private Long issueId;
    @NotNull
    private Long teamId;
}
