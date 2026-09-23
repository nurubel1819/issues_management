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

    @Query("select a from AssignForEstimation a " +
            "join fetch a.issue i " +
            "join fetch a.assign u " +
            "where i.sprint.id = :sprintId")
    List<AssignForEstimation> findByIssue_Sprint_Id(@Param("sprintId") Long sprintId);

    @Query("select a.estimationStatus as status, count(a) as total " +
            "from AssignForEstimation a where a.issue.sprint.id = :sprintId " +
            "group by a.estimationStatus")
    List<EstimationStatusCount> countByEstimationStatusForSprint(@Param("sprintId") Long sprintId);

    @Query("select a from AssignForEstimation a " +
            "join fetch a.issue i " +
            "join fetch a.assign u " +
            "where i.sprint.id = :sprintId " +
            "and (:estimationStatus is null or a.estimationStatus = :estimationStatus)")
    List<AssignForEstimation> findByIssue_Sprint_IdAndOptionalStatus(
            @Param("sprintId") Long sprintId,
            @Param("estimationStatus") EstimationStatus estimationStatus);

    interface EstimationStatusCount {
        EstimationStatus getStatus();
        Long getTotal();
    }
}
