package com.example.issues_management.domain.sprints.service;

import com.example.issues_management.common.exception.ResourceNotFoundException;
import com.example.issues_management.domain.issues.entitys.AssignForEstimation;
import com.example.issues_management.domain.issues.entitys.Issue;
import com.example.issues_management.domain.issues.enums.EstimationStatus;
import com.example.issues_management.domain.issues.enums.IssueStatus;

import com.example.issues_management.domain.issues.repo.AssignForEstimationRepository;
import com.example.issues_management.domain.issues.repo.IssueRepository;
import com.example.issues_management.domain.sprints.dtos.AssignmentDeliveryDetail;
import com.example.issues_management.domain.sprints.dtos.SprintReportResponse;
import com.example.issues_management.domain.sprints.entitys.Sprint;
import com.example.issues_management.domain.sprints.repo.SprintRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SprintReportService {

    private final SprintRepository sprintRepository;
    private final IssueRepository issueRepository;
    private final AssignForEstimationRepository assignForEstimationRepository;

    @Transactional(readOnly = true)
    public SprintReportResponse getSprintReport(Long sprintId, IssueStatus issueStatus, EstimationStatus estimationStatus) {

        Sprint sprint = sprintRepository.findById(sprintId)
                .orElseThrow(() -> new ResourceNotFoundException("Sprint not found with id: " + sprintId));

        // Filtered issue list
        List<Issue> issues = issueRepository.findBySprintIdAndOptionalStatus(sprintId, issueStatus);
        long totalIssues = issues.size();

        Map<String, Long> issueStatusDistribution = issueRepository.countByStatusForSprint(sprintId).stream()
                .collect(Collectors.toMap(
                        row -> row.getStatus().name(),
                        IssueRepository.IssueStatusCount::getTotal
                ));

        // Filtered assignment list
        List<AssignForEstimation> assignments = assignForEstimationRepository
                .findByIssue_Sprint_IdAndOptionalStatus(sprintId, estimationStatus);

        long totalAssignments = assignments.size();

        long totalDistinctDevelopers = assignments.stream()
                .map(a -> a.getAssign().getId())
                .distinct()
                .count();

        long totalMinutesRaw = assignments.stream()
                .mapToLong(a -> {
                    long hour = a.getEstimateHour() != null ? a.getEstimateHour() : 0;
                    long minute = a.getEstimateMinute() != null ? a.getEstimateMinute() : 0;
                    return (hour * 60) + minute;
                })
                .sum();
        long totalEstimatedHour = totalMinutesRaw / 60;
        long totalEstimatedMinute = totalMinutesRaw % 60;

        Map<String, Long> estimationStatusDistribution = assignForEstimationRepository
                .countByEstimationStatusForSprint(sprintId).stream()
                .collect(Collectors.toMap(
                        row -> row.getStatus().name(),
                        AssignForEstimationRepository.EstimationStatusCount::getTotal
                ));

        List<AssignmentDeliveryDetail> deliveries = assignments.stream()
                .map(a -> AssignmentDeliveryDetail.builder()
                        .issueId(a.getIssue().getId())
                        .issueTitle(a.getIssue().getTitle())
                        .userId(a.getAssign().getId())
                        .developerName(a.getAssign().getFullName())
                        .estimateHour(a.getEstimateHour())
                        .estimateMinute(a.getEstimateMinute())
                        .estimationStatus(a.getEstimationStatus())
                        .deliverDate(a.getDeliverDate())
                        .build())
                .collect(Collectors.toList());

        return SprintReportResponse.builder()
                .sprintId(sprint.getId())
                .sprintName(sprint.getName())
                .active(sprint.isActive())
                .startDate(sprint.getStartDate())
                .endDate(sprint.getEndDate())
                .filteredIssueStatus(issueStatus)
                .filteredEstimationStatus(estimationStatus)
                .totalIssues(totalIssues)
                .totalAssignments(totalAssignments)
                .totalDistinctDevelopers(totalDistinctDevelopers)
                .issueStatusDistribution(issueStatusDistribution)
                .totalEstimatedHour(totalEstimatedHour)
                .totalEstimatedMinute(totalEstimatedMinute)
                .estimationStatusDistribution(estimationStatusDistribution)
                .deliveries(deliveries)
                .build();
    }
}