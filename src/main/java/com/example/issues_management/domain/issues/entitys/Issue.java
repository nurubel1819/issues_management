package com.example.issues_management.domain.issues.entitys;

import com.example.issues_management.common.entity.BaseEntity;
import com.example.issues_management.domain.issues.enums.IssueStatus;
import com.example.issues_management.domain.issues.enums.IssueType;
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

    @Column(length = 1000)
    private String redmineLink;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IssueStatus issueStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private IssueType issueType;

    private LocalDate expectedCompletionDate;
    private LocalDate actualCompletionDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sprint_id")
    private Sprint sprint;
}