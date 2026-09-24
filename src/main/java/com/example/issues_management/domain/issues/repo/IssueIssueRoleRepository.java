package com.example.issues_management.domain.issues.repo;

import com.example.issues_management.domain.issues.entitys.IssueIssueRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IssueIssueRoleRepository extends JpaRepository<IssueIssueRole, Long> {

    @Query("select ir from IssueIssueRole ir " +
            "join fetch ir.issueRole " +
            "where ir.issue.id = :issueId")
    List<IssueIssueRole> findByIssueId(@Param("issueId") Long issueId);

    @Modifying
    @Query("delete from IssueIssueRole ir where ir.issue.id = :issueId")
    void deleteByIssueId(@Param("issueId") Long issueId);
}