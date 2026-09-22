package com.example.issues_management.domain.teams.repo;

import com.example.issues_management.domain.teams.entitys.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
    boolean existsByName(String name);
}
