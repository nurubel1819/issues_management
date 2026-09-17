package com.example.issues_management.issue.entitys;

import com.example.issues_management.auth.entity.User;
import com.example.issues_management.issue.enums.EstimationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Estimation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long estimateHour;
    private Long estimateMinute;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "issue_id", nullable = false)
    private Issue issue;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User assign;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private EstimationStatus estimationStatus;

    private Date deleverDate;
}
