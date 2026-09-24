package com.example.issues_management.domain.issues.entitys;

import com.example.issues_management.common.entity.BaseEntity;
import com.example.issues_management.domain.issues.enums.IssueStatus;
import com.example.issues_management.domain.projects.entitys.Project;
import com.example.issues_management.domain.sprints.entitys.Sprint;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@Entity
@Table(name = "issue")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Issue extends BaseEntity {

    @Column(nullable = false)
    private String title;

    private String issueTrackingNumber;

    @Column(length = 4000)
    private String description;

    @Column(length = 1000)
    private String link;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IssueStatus issueStatus;


    private LocalDate expectedCompletionDate;
    private LocalDate actualCompletionDate;

    // Issue -> Project (unidirectional many-to-one)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    // Issue -> Sprint (unidirectional many-to-one, nullable = issue may not be in a sprint yet)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sprint_id")
    private Sprint sprint;
}
