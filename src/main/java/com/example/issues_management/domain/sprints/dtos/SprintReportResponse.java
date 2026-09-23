package com.example.issues_management.domain.sprints.dtos;

import com.example.issues_management.domain.issues.enums.EstimationStatus;
import com.example.issues_management.domain.issues.enums.IssueStatus;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class SprintReportResponse {

    private Long sprintId;
    private String sprintName;
    private boolean active;
    private LocalDate startDate;
    private LocalDate endDate;

    private IssueStatus filteredIssueStatus;
    private EstimationStatus filteredEstimationStatus;

    private long totalIssues;
    private long totalAssignments;
    private long totalDistinctDevelopers;

    private Map<String, Long> issueStatusDistribution;

    private long totalEstimatedHour;
    private long totalEstimatedMinute;
    private Map<String, Long> estimationStatusDistribution;

    private List<AssignmentDeliveryDetail> deliveries;
}