package com.example.issues_management.domain.issues.repo;

import com.example.issues_management.domain.issues.entitys.AssignForEstimation;
import com.example.issues_management.domain.issues.enums.EstimationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AssignForEstimationRepository extends JpaRepository<AssignForEstimation, Long> {
    List<AssignForEstimation> findByIssueId(Long issueId);
    List<AssignForEstimation> findByAssignId(Long userId);
    @Query("select a from AssignForEstimation a " +
            "where a.assign.id = :userId " +
            "and (:status is null or a.estimationStatus = :status)")
    List<AssignForEstimation> findByAssignIdAndOptionalStatus(
            @Param("userId") Long userId,
            @Param("status") EstimationStatus status);
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

    @Query("select a.estimationStatus as status, count(a) as total, " +
            "coalesce(sum(a.estimateHour), 0) as totalHour, " +
            "coalesce(sum(a.estimateMinute), 0) as totalMinute " +
            "from AssignForEstimation a where a.assign.id = :userId " +
            "and (:status is null or a.estimationStatus = :status) " +
            "group by a.estimationStatus")
    List<UserEstimationStatusSummary> summarizeByUserAndOptionalStatus(
            @Param("userId") Long userId,
            @Param("status") EstimationStatus status);

    interface UserEstimationStatusSummary {
        EstimationStatus getStatus();
        Long getTotal();
        Long getTotalHour();
        Long getTotalMinute();
    }

    @Query("select max(a.deliverDate) from AssignForEstimation a " +
            "where a.assign.id = :userId " +
            "and (:status is null or a.estimationStatus = :status)")
    LocalDateTime findLastDeliverDateByUserAndOptionalStatus(
            @Param("userId") Long userId,
            @Param("status") EstimationStatus status);
}
