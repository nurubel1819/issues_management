package com.example.issues_management.domain.issues.entitys;

import com.example.issues_management.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "issue_issue_role",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_issue_issue_role",
                columnNames = {"issue_id", "issue_role_id"}
        )
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IssueIssueRole extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "issue_id", nullable = false)
    private Issue issue;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "issue_role_id", nullable = false)
    private IssueRole issueRole;
}