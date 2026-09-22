package com.example.issues_management.domain.projects.service;

import com.example.issues_management.common.exception.ResourceNotFoundException;
import com.example.issues_management.domain.projects.dtos.ProjectRequest;
import com.example.issues_management.domain.projects.dtos.ProjectResponse;
import com.example.issues_management.domain.projects.entitys.Project;
import com.example.issues_management.domain.projects.mapper.ProjectMapper;
import com.example.issues_management.domain.projects.repo.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    @Transactional
    public ProjectResponse createProject(ProjectRequest request) {
        if (projectRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Project with name '" + request.getName() + "' already exists");
        }
        Project saved = projectRepository.save(projectMapper.toEntity(request));
        return projectMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<ProjectResponse> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(projectMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProjectResponse getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));
        return projectMapper.toResponse(project);
    }

    @Transactional
    public ProjectResponse updateProject(Long id, ProjectRequest request) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));

        if (!project.getName().equals(request.getName()) && projectRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Project with name '" + request.getName() + "' already exists");
        }

        projectMapper.updateEntity(project, request);
        return projectMapper.toResponse(projectRepository.save(project));
    }

    @Transactional
    public void deleteProject(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new ResourceNotFoundException("Project not found with id: " + id);
        }
        projectRepository.deleteById(id);
    }
}
