package com.example.issues_management.domain.issues.dtos;

import com.example.issues_management.domain.issues.enums.EstimationStatus;
import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class AssignForEstimationResponse {
    private Long id;
    private Long issueId;
    private String issueTitle;
    private Long userId;
    private String userFullName;
    private Integer estimateHour;
    private Integer estimateMinute;
    private EstimationStatus estimationStatus;
    private LocalDateTime deliverDate;
}
