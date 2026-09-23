package com.example.issues_management.domain.issues.dtos;

import com.example.issues_management.domain.issues.enums.EstimationStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class UserEstimationDetailsResponse {
    private Long userId;
    private String userFullName;

    private EstimationStatus filteredStatus; // কোন filter apply হয়েছে, null মানে সব

    private long totalAssignments;
    private long totalEstimatedHour;
    private long totalEstimatedMinute;

    private LocalDateTime lastDeliverDate;

    private List<EstimationStatusSummary> statusWiseSummary;
}
