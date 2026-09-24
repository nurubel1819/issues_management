package com.example.issues_management.domain.issues.dtos;

import com.example.issues_management.domain.issues.enums.IssueStatus;
import com.example.issues_management.domain.issues.enums.IssueType;
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
    private String redmineLink;
    private IssueStatus issueStatus;
    private IssueType issueType;
    private LocalDate expectedCompletionDate;
    private LocalDate actualCompletionDate;
    private Long projectId;
    private String projectName;
    private Long sprintId;
    private String sprintName;
    private List<IssueRoleResponse> issueRoles;
}