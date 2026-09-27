package com.example.issues_management.domain.issues.controller;

import com.example.issues_management.domain.issues.dtos.IssueRequest;
import com.example.issues_management.domain.issues.dtos.IssueResponse;
import com.example.issues_management.domain.issues.service.IssueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.issues_management.domain.issues.dtos.PagedIssueResponse;

import java.util.List;

@RestController
@RequestMapping("/api/issues")
@RequiredArgsConstructor
@Tag(name = "Issue Management", description = "APIs for managing issues")
@SecurityRequirement(name = "bearerAuth")
public class IssueController {

    private final IssueService issueService;

    @PostMapping
    @Operation(summary = "Create a new issue")
    public ResponseEntity<IssueResponse> createIssue(@Valid @RequestBody IssueRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(issueService.createIssue(request));
    }

    @GetMapping
    @Operation(summary = "Get all issues")
    public ResponseEntity<List<IssueResponse>> getAllIssues() {
        return ResponseEntity.ok(issueService.getAllIssues());
    }

    @GetMapping("/v2")
    @Operation(summary = "Get all issues (v2)", description = "Returns paginated issue list, optionally filtered by title search")
    public ResponseEntity<PagedIssueResponse> getAllIssuesV2(
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(issueService.searchIssues(title, page, size));
    }

    @GetMapping("/project/{projectId}")
    @Operation(summary = "Get issues by project")
    public ResponseEntity<List<IssueResponse>> getIssuesByProject(@PathVariable Long projectId) {
        return ResponseEntity.ok(issueService.getIssuesByProject(projectId));
    }

    @GetMapping("/sprint/{sprintId}")
    @Operation(summary = "Get issues by sprint")
    public ResponseEntity<List<IssueResponse>> getIssuesBySprint(@PathVariable Long sprintId) {
        return ResponseEntity.ok(issueService.getIssuesBySprint(sprintId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get issue by ID")
    public ResponseEntity<IssueResponse> getIssueById(@PathVariable Long id) {
        return ResponseEntity.ok(issueService.getIssueById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an issue")
    public ResponseEntity<IssueResponse> updateIssue(@PathVariable Long id, @Valid @RequestBody IssueRequest request) {
        return ResponseEntity.ok(issueService.updateIssue(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an issue")
    public ResponseEntity<Void> deleteIssue(@PathVariable Long id) {
        issueService.deleteIssue(id);
        return ResponseEntity.noContent().build();
    }
}
