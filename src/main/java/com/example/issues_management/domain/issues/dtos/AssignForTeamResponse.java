package com.example.issues_management.domain.issues.dtos;

import lombok.*;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class AssignForTeamResponse {
    private Long id;
    private Long issueId;
    private String issueTitle;
    private Long teamId;
    private String teamName;
}
