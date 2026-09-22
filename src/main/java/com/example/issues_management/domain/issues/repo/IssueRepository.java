package com.example.issues_management.domain.issues.repo;

import com.example.issues_management.domain.issues.entitys.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Long> {
    List<Issue> findByProjectId(Long projectId);
    List<Issue> findBySprintId(Long sprintId);
}
