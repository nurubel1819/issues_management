package com.example.issues_management.domain.issues.repo;

import com.example.issues_management.domain.issues.entitys.AssignForTeam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignForTeamRepository extends JpaRepository<AssignForTeam, Long> {
    List<AssignForTeam> findByIssueId(Long issueId);
    List<AssignForTeam> findByTeamId(Long teamId);
    boolean existsByIssueIdAndTeamId(Long issueId, Long teamId);
}
