package com.example.issues_management.domain.issues.service;

import com.example.issues_management.common.exception.ResourceNotFoundException;
import com.example.issues_management.domain.issues.dtos.IssueRequest;
import com.example.issues_management.domain.issues.dtos.IssueResponse;
import com.example.issues_management.domain.issues.entitys.Issue;
import com.example.issues_management.domain.issues.mapper.IssueMapper;
import com.example.issues_management.domain.issues.repo.IssueRepository;
import com.example.issues_management.domain.projects.entitys.Project;
import com.example.issues_management.domain.projects.repo.ProjectRepository;
import com.example.issues_management.domain.sprints.entitys.Sprint;
import com.example.issues_management.domain.sprints.repo.SprintRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IssueService {

    private final IssueRepository issueRepository;
    private final ProjectRepository projectRepository;
    private final SprintRepository sprintRepository;
    private final IssueMapper issueMapper;

    @Transactional
    public IssueResponse createIssue(IssueRequest request) {
        Project project = resolveProject(request.getProjectId());
        Sprint sprint = resolveSprint(request.getSprintId());

        Issue saved = issueRepository.save(issueMapper.toEntity(request, project, sprint));
        return issueMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<IssueResponse> getAllIssues() {
        return issueRepository.findAll().stream()
                .map(issueMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<IssueResponse> getIssuesByProject(Long projectId) {
        return issueRepository.findByProjectId(projectId).stream()
                .map(issueMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<IssueResponse> getIssuesBySprint(Long sprintId) {
        return issueRepository.findBySprintId(sprintId).stream()
                .map(issueMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public IssueResponse getIssueById(Long id) {
        Issue issue = issueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Issue not found with id: " + id));
        return issueMapper.toResponse(issue);
    }

    @Transactional
    public IssueResponse updateIssue(Long id, IssueRequest request) {
        Issue issue = issueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Issue not found with id: " + id));

        Project project = resolveProject(request.getProjectId());
        Sprint sprint = resolveSprint(request.getSprintId());

        issueMapper.updateEntity(issue, request, project, sprint);
        return issueMapper.toResponse(issueRepository.save(issue));
    }

    @Transactional
    public void deleteIssue(Long id) {
        if (!issueRepository.existsById(id)) {
            throw new ResourceNotFoundException("Issue not found with id: " + id);
        }
        issueRepository.deleteById(id);
    }

    private Project resolveProject(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + projectId));
    }

    private Sprint resolveSprint(Long sprintId) {
        if (sprintId == null) {
            return null;
        }
        return sprintRepository.findById(sprintId)
                .orElseThrow(() -> new ResourceNotFoundException("Sprint not found with id: " + sprintId));
    }
}
