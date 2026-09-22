package com.example.issues_management.domain.issues.dtos;

import com.example.issues_management.domain.issues.enums.EstimationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class AssignForEstimationRequest {
    @NotNull
    private Long issueId;
    @NotNull
    private Long userId;
    private Integer estimateHour;
    private Integer estimateMinute;
    @NotNull
    private EstimationStatus estimationStatus;
    private LocalDateTime deliverDate;
}
