package com.example.issues_management.domain.teams.service;

import com.example.issues_management.common.exception.ResourceNotFoundException;
import com.example.issues_management.domain.teams.dtos.TeamRequest;
import com.example.issues_management.domain.teams.dtos.TeamResponse;
import com.example.issues_management.domain.teams.entitys.Team;
import com.example.issues_management.domain.teams.mapper.TeamMapper;
import com.example.issues_management.domain.teams.repo.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;

    @Transactional
    public TeamResponse createTeam(TeamRequest request) {
        if (teamRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Team with name '" + request.getName() + "' already exists");
        }
        Team saved = teamRepository.save(teamMapper.toEntity(request));
        return teamMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<TeamResponse> getAllTeams() {
        return teamRepository.findAll().stream()
                .map(teamMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TeamResponse getTeamById(Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found with id: " + id));
        return teamMapper.toResponse(team);
    }

    @Transactional
    public TeamResponse updateTeam(Long id, TeamRequest request) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found with id: " + id));

        if (!team.getName().equals(request.getName()) && teamRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Team with name '" + request.getName() + "' already exists");
        }

        teamMapper.updateEntity(team, request);
        return teamMapper.toResponse(teamRepository.save(team));
    }

    @Transactional
    public void deleteTeam(Long id) {
        if (!teamRepository.existsById(id)) {
            throw new ResourceNotFoundException("Team not found with id: " + id);
        }
        teamRepository.deleteById(id);
    }
}
