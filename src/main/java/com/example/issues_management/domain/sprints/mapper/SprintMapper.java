package com.example.issues_management.domain.sprints.mapper;


import com.example.issues_management.domain.sprints.dtos.SprintRequest;
import com.example.issues_management.domain.sprints.dtos.SprintResponse;
import com.example.issues_management.domain.sprints.entitys.Sprint;
import org.springframework.stereotype.Component;

@Component
public class SprintMapper {

    public Sprint toEntity(SprintRequest request) {
        return Sprint.builder()
                .name(request.getName())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .active(request.isActive())
                .build();
    }

    public void updateEntity(Sprint sprint, SprintRequest request) {
        sprint.setName(request.getName());
        sprint.setStartDate(request.getStartDate());
        sprint.setEndDate(request.getEndDate());
        sprint.setActive(request.isActive());
    }

    public SprintResponse toResponse(Sprint sprint) {
        return SprintResponse.builder()
                .id(sprint.getId())
                .name(sprint.getName())
                .startDate(sprint.getStartDate())
                .endDate(sprint.getEndDate())
                .active(sprint.isActive())
                .build();
    }
}
