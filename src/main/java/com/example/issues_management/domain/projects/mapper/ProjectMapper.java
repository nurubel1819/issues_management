package com.example.issues_management.domain.projects.mapper;



import com.example.issues_management.domain.projects.dtos.ProjectRequest;
import com.example.issues_management.domain.projects.dtos.ProjectResponse;
import com.example.issues_management.domain.projects.entitys.Project;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {

    public Project toEntity(ProjectRequest request) {
        return Project.builder()
                .name(request.getName())
                .description(request.getDescription())
                .ownerName(request.getOwnerName())
                .build();
    }

    public void updateEntity(Project project, ProjectRequest request) {
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setOwnerName(request.getOwnerName());
    }

    public ProjectResponse toResponse(Project project) {
        return ProjectResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .ownerName(project.getOwnerName())
                .createdAt(project.getCreatedAt())
                .updatedAt(project.getUpdatedAt())
                .build();
    }
}
