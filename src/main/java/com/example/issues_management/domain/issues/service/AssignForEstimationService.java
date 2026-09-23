package com.example.issues_management.domain.issues.service;

import com.example.issues_management.auth.entity.User;
import com.example.issues_management.auth.repository.UserRepository;
import com.example.issues_management.common.exception.ResourceNotFoundException;
import com.example.issues_management.domain.issues.dtos.AssignForEstimationRequest;
import com.example.issues_management.domain.issues.dtos.AssignForEstimationResponse;
import com.example.issues_management.domain.issues.dtos.EstimationStatusSummary;
import com.example.issues_management.domain.issues.dtos.UserEstimationDetailsResponse;
import com.example.issues_management.domain.issues.entitys.AssignForEstimation;
import com.example.issues_management.domain.issues.entitys.Issue;
import com.example.issues_management.domain.issues.enums.EstimationStatus;
import com.example.issues_management.domain.issues.mapper.AssignForEstimationMapper;
import com.example.issues_management.domain.issues.repo.AssignForEstimationRepository;
import com.example.issues_management.domain.issues.repo.IssueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssignForEstimationService {

    private final AssignForEstimationRepository repository;
    private final IssueRepository issueRepository;
    private final UserRepository userRepository;
    private final AssignForEstimationMapper mapper;

    @Transactional
    public AssignForEstimationResponse assign(AssignForEstimationRequest request) {
        if (repository.existsByIssueIdAndAssignId(request.getIssueId(), request.getUserId())) {
            throw new IllegalStateException("This user already assign");
        }

        Issue issue = issueRepository.findById(request.getIssueId())
                .orElseThrow(() -> new ResourceNotFoundException("Issue not found with id: " + request.getIssueId()));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + request.getUserId()));

        AssignForEstimation saved = repository.save(mapper.toEntity(request, issue, user));
        return mapper.toResponse(saved);
    }

    @Transactional
    public AssignForEstimationResponse updateEstimation(Long id, AssignForEstimationRequest request) {
        AssignForEstimation entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Assignment not found with id: " + id));

        mapper.updateEntity(entity, request);
        return mapper.toResponse(repository.save(entity));
    }

    @Transactional(readOnly = true)
    public List<AssignForEstimationResponse> getByIssue(Long issueId) {
        return repository.findByIssueId(issueId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AssignForEstimationResponse> getByUser(Long userId, EstimationStatus status) {
        return repository.findByAssignIdAndOptionalStatus(userId, status).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void remove(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Assignment not found with id: " + id);
        }
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public UserEstimationDetailsResponse getUserEstimationDetails(Long userId, EstimationStatus status) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        List<AssignForEstimationRepository.UserEstimationStatusSummary> rows =
                repository.summarizeByUserAndOptionalStatus(userId, status);

        List<EstimationStatusSummary> statusWiseSummary = rows.stream()
                .map(row -> {
                    long totalMinutesRaw = (row.getTotalHour() * 60) + row.getTotalMinute();
                    return EstimationStatusSummary.builder()
                            .status(row.getStatus())
                            .count(row.getTotal())
                            .totalHour(totalMinutesRaw / 60)
                            .totalMinute(totalMinutesRaw % 60)
                            .build();
                })
                .collect(Collectors.toList());

        long totalAssignments = statusWiseSummary.stream()
                .mapToLong(EstimationStatusSummary::getCount)
                .sum();

        long grandTotalMinutes = statusWiseSummary.stream()
                .mapToLong(s -> (s.getTotalHour() * 60) + s.getTotalMinute())
                .sum();

        LocalDateTime lastDeliverDate = repository.findLastDeliverDateByUserAndOptionalStatus(userId, status);

        return UserEstimationDetailsResponse.builder()
                .userId(user.getId())
                .userFullName(user.getFullName())
                .filteredStatus(status)
                .totalAssignments(totalAssignments)
                .totalEstimatedHour(grandTotalMinutes / 60)
                .totalEstimatedMinute(grandTotalMinutes % 60)
                .lastDeliverDate(lastDeliverDate)
                .statusWiseSummary(statusWiseSummary)
                .build();
    }
}
