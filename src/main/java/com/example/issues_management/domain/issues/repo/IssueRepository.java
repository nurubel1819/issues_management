package com.example.issues_management.domain.issues.repo;

import com.example.issues_management.domain.issues.entitys.Issue;
import com.example.issues_management.domain.issues.enums.IssueStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Long> {
    List<Issue> findByProjectId(Long projectId);
    List<Issue> findBySprintId(Long sprintId);

    // Sprint-এর issue গুলো status-wise group করে count
    @Query("select i.issueStatus as status, count(i) as total " +
            "from Issue i where i.sprint.id = :sprintId " +
            "group by i.issueStatus")
    List<IssueStatusCount> countByStatusForSprint(@Param("sprintId") Long sprintId);

    interface IssueStatusCount {
        IssueStatus getStatus();
        Long getTotal();
    }
}
