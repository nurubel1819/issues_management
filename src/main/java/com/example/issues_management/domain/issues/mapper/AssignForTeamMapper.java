package com.example.issues_management.domain.issues.mapper;


import com.example.issues_management.domain.issues.dtos.AssignForTeamResponse;
import com.example.issues_management.domain.issues.entitys.AssignForTeam;
import com.example.issues_management.domain.issues.entitys.Issue;
import com.example.issues_management.domain.teams.entitys.Team;
import org.springframework.stereotype.Component;

@Component
public class AssignForTeamMapper {

    public AssignForTeam toEntity(Issue issue, Team team) {
        return AssignForTeam.builder()
                .issue(issue)
                .team(team)
                .build();
    }

    public AssignForTeamResponse toResponse(AssignForTeam entity) {
        return AssignForTeamResponse.builder()
                .id(entity.getId())
                .issueId(entity.getIssue().getId())
                .issueTitle(entity.getIssue().getTitle())
                .teamId(entity.getTeam().getId())
                .teamName(entity.getTeam().getName())
                .build();
    }
}
