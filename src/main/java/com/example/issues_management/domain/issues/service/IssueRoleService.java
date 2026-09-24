package com.example.issues_management.domain.issues.service;

import com.example.issues_management.common.exception.ResourceNotFoundException;
import com.example.issues_management.domain.issues.dtos.IssueRoleRequest;
import com.example.issues_management.domain.issues.dtos.IssueRoleResponse;
import com.example.issues_management.domain.issues.entitys.IssueRole;
import com.example.issues_management.domain.issues.mapper.IssueRoleMapper;
import com.example.issues_management.domain.issues.repo.IssueRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IssueRoleService {

    private final IssueRoleRepository issueRoleRepository;
    private final IssueRoleMapper issueRoleMapper;

    @Transactional
    public IssueRoleResponse createIssueRole(IssueRoleRequest request) {
        if (issueRoleRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Issue role with name '" + request.getName() + "' already exists");
        }
        IssueRole saved = issueRoleRepository.save(issueRoleMapper.toEntity(request));
        return issueRoleMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<IssueRoleResponse> getAllIssueRoles() {
        return issueRoleRepository.findAll().stream()
                .map(issueRoleMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public IssueRoleResponse getIssueRoleById(Long id) {
        IssueRole issueRole = issueRoleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Issue role not found with id: " + id));
        return issueRoleMapper.toResponse(issueRole);
    }

    @Transactional
    public IssueRoleResponse updateIssueRole(Long id, IssueRoleRequest request) {
        IssueRole issueRole = issueRoleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Issue role not found with id: " + id));

        if (!issueRole.getName().equals(request.getName()) && issueRoleRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Issue role with name '" + request.getName() + "' already exists");
        }

        issueRoleMapper.updateEntity(issueRole, request);
        return issueRoleMapper.toResponse(issueRoleRepository.save(issueRole));
    }

    @Transactional
    public void deleteIssueRole(Long id) {
        if (!issueRoleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Issue role not found with id: " + id);
        }
        issueRoleRepository.deleteById(id);
    }
}