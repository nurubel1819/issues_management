package com.example.issues_management.domain.issues.mapper;

import com.example.issues_management.domain.issues.dtos.IssueRequest;
import com.example.issues_management.domain.issues.dtos.IssueResponse;
import com.example.issues_management.domain.issues.entitys.Issue;
import com.example.issues_management.domain.projects.entitys.Project;
import com.example.issues_management.domain.sprints.entitys.Sprint;
import org.springframework.stereotype.Component;

@Component
public class IssueMapper {

    public Issue toEntity(IssueRequest request, Project project, Sprint sprint) {
        return Issue.builder()
                .title(request.getTitle())
                .issueTrackingNumber(request.getIssueTrackingNumber())
                .description(request.getDescription())
                .link(request.getLink())
                .redmineLink(request.getRedmineLink())
                .issueStatus(request.getIssueStatus())
                .issueType(request.getIssueType())
                .expectedCompletionDate(request.getExpectedCompletionDate())
                .actualCompletionDate(request.getActualCompletionDate())
                .project(project)
                .sprint(sprint)
                .build();
    }

    public void updateEntity(Issue issue, IssueRequest request, Project project, Sprint sprint) {
        issue.setTitle(request.getTitle());
        issue.setIssueTrackingNumber(request.getIssueTrackingNumber());
        issue.setDescription(request.getDescription());
        issue.setLink(request.getLink());
        issue.setRedmineLink(request.getRedmineLink());
        issue.setIssueStatus(request.getIssueStatus());
        issue.setIssueType(request.getIssueType());
        issue.setExpectedCompletionDate(request.getExpectedCompletionDate());
        issue.setActualCompletionDate(request.getActualCompletionDate());
        issue.setProject(project);
        issue.setSprint(sprint);
    }

    public IssueResponse toResponseWithoutRoles(Issue issue) {
        return IssueResponse.builder()
                .id(issue.getId())
                .title(issue.getTitle())
                .issueTrackingNumber(issue.getIssueTrackingNumber())
                .description(issue.getDescription())
                .link(issue.getLink())
                .redmineLink(issue.getRedmineLink())
                .issueStatus(issue.getIssueStatus())
                .issueType(issue.getIssueType())
                .expectedCompletionDate(issue.getExpectedCompletionDate())
                .actualCompletionDate(issue.getActualCompletionDate())
                .projectId(issue.getProject().getId())
                .projectName(issue.getProject().getName())
                .sprintId(issue.getSprint() != null ? issue.getSprint().getId() : null)
                .sprintName(issue.getSprint() != null ? issue.getSprint().getName() : null)
                .build();
    }
}