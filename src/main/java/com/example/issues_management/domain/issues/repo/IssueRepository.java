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

    @Query("select i.issueStatus as status, count(i) as total " +
            "from Issue i where i.sprint.id = :sprintId " +
            "group by i.issueStatus")
    List<IssueStatusCount> countByStatusForSprint(@Param("sprintId") Long sprintId);

    @Query("select i from Issue i where i.sprint.id = :sprintId " +
            "and (:issueStatus is null or i.issueStatus = :issueStatus)")
    List<Issue> findBySprintIdAndOptionalStatus(
            @Param("sprintId") Long sprintId,
            @Param("issueStatus") IssueStatus issueStatus);

    interface IssueStatusCount {
        IssueStatus getStatus();
        Long getTotal();
    }
}
