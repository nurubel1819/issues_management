package com.example.issues_management.domain.issues.repo;

import com.example.issues_management.domain.issues.entitys.AssignForEstimation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AssignForEstimationRepository extends JpaRepository<AssignForEstimation, Long> {
    List<AssignForEstimation> findByIssueId(Long issueId);
    List<AssignForEstimation> findByAssignId(Long userId);
    Optional<AssignForEstimation> findByIssueIdAndAssignId(Long issueId, Long userId);
    boolean existsByIssueIdAndAssignId(Long issueId, Long userId);
}
