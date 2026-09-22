package com.example.issues_management.domain.projects.repo;

import com.example.issues_management.domain.projects.entitys.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    boolean existsByName(String name);
}
