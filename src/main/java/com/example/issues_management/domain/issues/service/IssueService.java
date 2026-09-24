package com.example.issues_management.domain.issues.service;

import com.example.issues_management.common.exception.ResourceNotFoundException;
import com.example.issues_management.domain.issues.dtos.IssueRequest;
import com.example.issues_management.domain.issues.dtos.IssueResponse;
import com.example.issues_management.domain.issues.dtos.IssueRoleResponse;
import com.example.issues_management.domain.issues.entitys.Issue;
import com.example.issues_management.domain.issues.entitys.IssueIssueRole;
import com.example.issues_management.domain.issues.entitys.IssueRole;
import com.example.issues_management.domain.issues.mapper.IssueMapper;
import com.example.issues_management.domain.issues.repo.IssueIssueRoleRepository;
import com.example.issues_management.domain.issues.repo.IssueRepository;
import com.example.issues_management.domain.issues.repo.IssueRoleRepository;
import com.example.issues_management.domain.projects.entitys.Project;
import com.example.issues_management.domain.projects.repo.ProjectRepository;
import com.example.issues_management.domain.sprints.entitys.Sprint;
import com.example.issues_management.domain.sprints.repo.SprintRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IssueService {

    private final IssueRepository issueRepository;
    private final ProjectRepository projectRepository;
    private final SprintRepository sprintRepository;
    private final IssueRoleRepository issueRoleRepository;
    private final IssueIssueRoleRepository issueIssueRoleRepository;
    private final IssueMapper issueMapper;

    @Transactional
    public IssueResponse createIssue(IssueRequest request) {
        Project project = resolveProject(request.getProjectId());
        Sprint sprint = resolveSprint(request.getSprintId());

        // ধাপ ১ — প্রথমে Issue টা save করে ফেলা হচ্ছে (এতে issue.id পাওয়া যায়)
        Issue savedIssue = issueRepository.save(issueMapper.toEntity(request, project, sprint));

        // ধাপ ২ — একই request-এর issueRoleIds ব্যবহার করে join row গুলো তৈরি করা হচ্ছে
        attachIssueRoles(savedIssue, request.getIssueRoleIds());

        // ধাপ ৩ — issue + assigned roles একসাথে response-এ ফেরত দেওয়া হচ্ছে
        return toFullResponse(savedIssue);
    }

    @Transactional(readOnly = true)
    public List<IssueResponse> getAllIssues() {
        return issueRepository.findAll().stream()
                .map(this::toFullResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<IssueResponse> getIssuesByProject(Long projectId) {
        return issueRepository.findByProjectId(projectId).stream()
                .map(this::toFullResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<IssueResponse> getIssuesBySprint(Long sprintId) {
        return issueRepository.findBySprintId(sprintId).stream()
                .map(this::toFullResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public IssueResponse getIssueById(Long id) {
        Issue issue = issueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Issue not found with id: " + id));
        return toFullResponse(issue);
    }

    @Transactional
    public IssueResponse updateIssue(Long id, IssueRequest request) {
        Issue issue = issueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Issue not found with id: " + id));

        Project project = resolveProject(request.getProjectId());
        Sprint sprint = resolveSprint(request.getSprintId());

        issueMapper.updateEntity(issue, request, project, sprint);
        Issue updatedIssue = issueRepository.save(issue);

        // update-এ পুরনো role assignment মুছে নতুন করে সেট করা হচ্ছে (replace strategy)
        issueIssueRoleRepository.deleteByIssueId(updatedIssue.getId());
        attachIssueRoles(updatedIssue, request.getIssueRoleIds());

        return toFullResponse(updatedIssue);
    }

    @Transactional
    public void deleteIssue(Long id) {
        if (!issueRepository.existsById(id)) {
            throw new ResourceNotFoundException("Issue not found with id: " + id);
        }
        issueIssueRoleRepository.deleteByIssueId(id);
        issueRepository.deleteById(id);
    }

    // ============ Helper Methods ============

    private void attachIssueRoles(Issue issue, List<Long> issueRoleIds) {
        if (issueRoleIds == null || issueRoleIds.isEmpty()) {
            return; // role না দিলে কিছু করার দরকার নেই — এটা optional
        }

        List<IssueRole> roles = issueRoleRepository.findAllById(issueRoleIds);

        // পাঠানো id-এর সংখ্যা আর পাওয়া role-এর সংখ্যা না মিললে মানে কোনো invalid id ছিল
        if (roles.size() != issueRoleIds.size()) {
            throw new ResourceNotFoundException("One or more issue role IDs are invalid");
        }

        List<IssueIssueRole> joinRows = roles.stream()
                .map(role -> IssueIssueRole.builder()
                        .issue(issue)
                        .issueRole(role)
                        .build())
                .collect(Collectors.toList());

        issueIssueRoleRepository.saveAll(joinRows);
    }

    private IssueResponse toFullResponse(Issue issue) {
        IssueResponse response = issueMapper.toResponseWithoutRoles(issue);

        List<IssueRoleResponse> roles = issueIssueRoleRepository.findByIssueId(issue.getId()).stream()
                .map(link -> IssueRoleResponse.builder()
                        .id(link.getIssueRole().getId())
                        .name(link.getIssueRole().getName())
                        .description(link.getIssueRole().getDescription())
                        .build())
                .collect(Collectors.toList());

        response.setIssueRoles(roles);
        return response;
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