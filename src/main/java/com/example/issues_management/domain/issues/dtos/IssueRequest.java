package com.example.issues_management.domain.issues.dtos;

import com.example.issues_management.domain.issues.enums.IssueStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDate;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class IssueRequest {
    @NotBlank
    private String title;
    private String issueTrackingNumber;
    private String description;
    private String link;
    @NotNull
    private IssueStatus issueStatus;
    private LocalDate expectedCompletionDate;
    private LocalDate actualCompletionDate;
    @NotNull
    private Long projectId;
    private Long sprintId; // nullable — issue may not be in a sprint yet
}
