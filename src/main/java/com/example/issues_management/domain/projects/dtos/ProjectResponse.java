package com.example.issues_management.domain.projects.dtos;


import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class ProjectResponse {
    private Long id;
    private String name;
    private String description;
    private String ownerName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
