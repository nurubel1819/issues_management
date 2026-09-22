package com.example.issues_management.domain.issues.service;

import com.example.issues_management.common.exception.ResourceNotFoundException;
import com.example.issues_management.domain.issues.dtos.AssignForTeamRequest;
import com.example.issues_management.domain.issues.dtos.AssignForTeamResponse;
import com.example.issues_management.domain.issues.entitys.AssignForTeam;
import com.example.issues_management.domain.issues.entitys.Issue;
import com.example.issues_management.domain.issues.mapper.AssignForTeamMapper;
import com.example.issues_management.domain.issues.repo.AssignForTeamRepository;
import com.example.issues_management.domain.issues.repo.IssueRepository;
import com.example.issues_management.domain.teams.entitys.Team;
import com.example.issues_management.domain.teams.repo.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssignForTeamService {

    private final AssignForTeamRepository repository;
    private final IssueRepository issueRepository;
    private final TeamRepository teamRepository;
    private final AssignForTeamMapper mapper;

    @Transactional
    public AssignForTeamResponse assign(AssignForTeamRequest request) {
        if (repository.existsByIssueIdAndTeamId(request.getIssueId(), request.getTeamId())) {
            throw new IllegalStateException("this team already assign");
        }

        Issue issue = issueRepository.findById(request.getIssueId())
                .orElseThrow(() -> new ResourceNotFoundException("Issue not found with id: " + request.getIssueId()));

        Team team = teamRepository.findById(request.getTeamId())
                .orElseThrow(() -> new ResourceNotFoundException("Team not found with id: " + request.getTeamId()));

        AssignForTeam saved = repository.save(mapper.toEntity(issue, team));
        return mapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<AssignForTeamResponse> getByIssue(Long issueId) {
        return repository.findByIssueId(issueId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AssignForTeamResponse> getByTeam(Long teamId) {
        return repository.findByTeamId(teamId).stream()
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
}
