package com.example.issues_management.domain.sprints.repo;

import com.example.issues_management.domain.sprints.entitys.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SprintRepository extends JpaRepository<Sprint, Long> {
    List<Sprint> findByActiveTrue();
}
