package com.example.issues_management.domain.issues.dtos;

import com.example.issues_management.domain.issues.enums.IssueStatus;
import com.example.issues_management.domain.issues.enums.IssueType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class IssueRequest {
    @NotBlank
    private String title;

    private String issueTrackingNumber;
    private String description;
    private String link;
    private String redmineLink;

    @NotNull
    private IssueStatus issueStatus;

    @NotNull
    private IssueType issueType;

    private LocalDate expectedCompletionDate;
    private LocalDate actualCompletionDate;

    @NotNull
    private Long projectId;
    private Long sprintId;

    private List<Long> issueRoleIds;
}