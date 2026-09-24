package com.example.issues_management.domain.issues.mapper;

import com.example.issues_management.domain.issues.dtos.IssueRoleRequest;
import com.example.issues_management.domain.issues.dtos.IssueRoleResponse;
import com.example.issues_management.domain.issues.entitys.IssueRole;
import org.springframework.stereotype.Component;

@Component
public class IssueRoleMapper {

    public IssueRole toEntity(IssueRoleRequest request) {
        return IssueRole.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }

    public void updateEntity(IssueRole issueRole, IssueRoleRequest request) {
        issueRole.setName(request.getName());
        issueRole.setDescription(request.getDescription());
    }

    public IssueRoleResponse toResponse(IssueRole issueRole) {
        return IssueRoleResponse.builder()
                .id(issueRole.getId())
                .name(issueRole.getName())
                .description(issueRole.getDescription())
                .build();
    }
}