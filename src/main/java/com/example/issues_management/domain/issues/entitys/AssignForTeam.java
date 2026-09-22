package com.example.issues_management.domain.issues.entitys;

import com.example.issues_management.common.entity.BaseEntity;
import com.example.issues_management.domain.teams.entitys.Team;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "assign_for_team",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_issue_team",
                columnNames = {"issue_id", "team_id"}
        )
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignForTeam extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "issue_id", nullable = false)
    private Issue issue;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;
}
