package com.example.issues_management.domain.issues.entitys;

import com.example.issues_management.auth.entity.User;
import com.example.issues_management.common.entity.BaseEntity;
import com.example.issues_management.domain.issues.enums.EstimationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "assign_for_estimation",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_issue_user_estimation",
                columnNames = {"issue_id", "user_id"}
        )
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignForEstimation extends BaseEntity {
    private Integer estimateHour;
    private Integer estimateMinute;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "issue_id", nullable = false)
    private Issue issue;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User assign;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private EstimationStatus estimationStatus;

    private LocalDateTime deliverDate;
}
