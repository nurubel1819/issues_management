package com.example.issues_management.domain.teams.mapper;

import com.example.issues_management.domain.teams.dtos.TeamRequest;
import com.example.issues_management.domain.teams.dtos.TeamResponse;
import com.example.issues_management.domain.teams.entitys.Team;
import org.springframework.stereotype.Component;

@Component
public class TeamMapper {

    public Team toEntity(TeamRequest request) {
        return Team.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }

    public void updateEntity(Team team, TeamRequest request) {
        team.setName(request.getName());
        team.setDescription(request.getDescription());
    }

    public TeamResponse toResponse(Team team) {
        return TeamResponse.builder()
                .id(team.getId())
                .name(team.getName())
                .description(team.getDescription())
                .build();
    }
}
