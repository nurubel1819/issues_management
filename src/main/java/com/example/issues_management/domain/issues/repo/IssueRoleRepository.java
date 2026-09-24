package com.example.issues_management.domain.issues.repo;

import com.example.issues_management.domain.issues.entitys.IssueRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRoleRepository extends JpaRepository<IssueRole, Long> {
    boolean existsByName(String name);
}