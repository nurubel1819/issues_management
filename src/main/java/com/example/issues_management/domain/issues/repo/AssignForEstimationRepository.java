package com.example.issues_management.domain.issues.repo;

import com.example.issues_management.domain.issues.entitys.AssignForEstimation;
import com.example.issues_management.domain.issues.enums.EstimationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AssignForEstimationRepository extends JpaRepository<AssignForEstimation, Long> {
    List<AssignForEstimation> findByIssueId(Long issueId);
    List<AssignForEstimation> findByAssignId(Long userId);
    Optional<AssignForEstimation> findByIssueIdAndAssignId(Long issueId, Long userId);
    boolean existsByIssueIdAndAssignId(Long issueId, Long userId);

    List<AssignForEstimation> findByIssue_Sprint_Id(Long sprintId);

    @Query("select a.estimationStatus as status, count(a) as total " +
            "from AssignForEstimation a where a.issue.sprint.id = :sprintId " +
            "group by a.estimationStatus")
    List<EstimationStatusCount> countByEstimationStatusForSprint(@Param("sprintId") Long sprintId);

    interface EstimationStatusCount {
        EstimationStatus getStatus();
        Long getTotal();
    }
}
