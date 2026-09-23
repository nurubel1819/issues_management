package com.example.issues_management.domain.issues.dtos;

import com.example.issues_management.domain.issues.enums.EstimationStatus;
import lombok.*;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class EstimationStatusSummary {
    private EstimationStatus status;
    private long count;
    private long totalHour;
    private long totalMinute;
}
