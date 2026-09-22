package com.example.issues_management.domain.issues.mapper;

import com.example.issues_management.auth.entity.User;
import com.example.issues_management.domain.issues.dtos.AssignForEstimationRequest;
import com.example.issues_management.domain.issues.dtos.AssignForEstimationResponse;
import com.example.issues_management.domain.issues.entitys.AssignForEstimation;
import com.example.issues_management.domain.issues.entitys.Issue;
import org.springframework.stereotype.Component;

@Component
public class AssignForEstimationMapper {

    public AssignForEstimation toEntity(AssignForEstimationRequest request, Issue issue, User user) {
        return AssignForEstimation.builder()
                .issue(issue)
                .assign(user)
                .estimateHour(request.getEstimateHour())
                .estimateMinute(request.getEstimateMinute())
                .estimationStatus(request.getEstimationStatus())
                .build();
    }

    public void updateEntity(AssignForEstimation entity, AssignForEstimationRequest request) {
        entity.setEstimateHour(request.getEstimateHour());
        entity.setEstimateMinute(request.getEstimateMinute());
        entity.setEstimationStatus(request.getEstimationStatus());
    }

    public AssignForEstimationResponse toResponse(AssignForEstimation entity) {
        return AssignForEstimationResponse.builder()
                .id(entity.getId())
                .issueId(entity.getIssue().getId())
                .issueTitle(entity.getIssue().getTitle())
                .userId(entity.getAssign().getId())
                .userFullName(entity.getAssign().getFullName())
                .estimateHour(entity.getEstimateHour())
                .estimateMinute(entity.getEstimateMinute())
                .estimationStatus(entity.getEstimationStatus())
                .deliverDate(entity.getDeliverDate())
                .build();
    }
}
