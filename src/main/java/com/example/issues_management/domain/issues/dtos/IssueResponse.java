package com.example.issues_management.domain.issues.dtos;

import com.example.issues_management.domain.issues.enums.IssueStatus;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class IssueResponse {
    private Long id;
    private String title;
    private String issueTrackingNumber;
    private String description;
    private String link;
    private IssueStatus issueStatus;
    private LocalDate expectedCompletionDate;
    private LocalDate actualCompletionDate;
    private Long projectId;
    private String projectName;
    private Long sprintId;
    private String sprintName;
    private List<IssueRoleResponse> issueRoles;
}
