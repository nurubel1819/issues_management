package com.example.issues_management.domain.sprints.dtos;

import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class SprintReportResponse {

    // Sprint details
    private Long sprintId;
    private String sprintName;
    private boolean active;
    private LocalDate startDate;
    private LocalDate endDate;

    // Overview
    private long totalIssues;
    private long totalAssignments;
    private long totalDistinctDevelopers;

    // Issue status wise distribution (issue entity থেকে)
    private Map<String, Long> issueStatusDistribution;

    // Estimation
    private long totalEstimatedHour;
    private long totalEstimatedMinute;
    private Map<String, Long> estimationStatusDistribution;

    // Delivery details — কোন developer কোন issue-তে কবে deliver করবে
    private List<AssignmentDeliveryDetail> deliveries;
}
