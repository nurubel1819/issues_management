package com.example.issues_management.domain.issues.controller;

import com.example.issues_management.domain.issues.dtos.IssueRoleRequest;
import com.example.issues_management.domain.issues.dtos.IssueRoleResponse;
import com.example.issues_management.domain.issues.service.IssueRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issue-roles")
@RequiredArgsConstructor
@Tag(name = "Issue Role Management", description = "APIs for managing issue roles (e.g. Agent App, User App, Doctor, STM)")
@SecurityRequirement(name = "bearerAuth")
public class IssueRoleController {

    private final IssueRoleService issueRoleService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new issue role", description = "Creates a new issue role. Requires ADMIN role.")
    public ResponseEntity<IssueRoleResponse> createIssueRole(@Valid @RequestBody IssueRoleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(issueRoleService.createIssueRole(request));
    }

    @GetMapping
    @Operation(summary = "Get all issue roles")
    public ResponseEntity<List<IssueRoleResponse>> getAllIssueRoles() {
        return ResponseEntity.ok(issueRoleService.getAllIssueRoles());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get issue role by ID")
    public ResponseEntity<IssueRoleResponse> getIssueRoleById(@PathVariable Long id) {
        return ResponseEntity.ok(issueRoleService.getIssueRoleById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update an issue role", description = "Updates an existing issue role. Requires ADMIN role.")
    public ResponseEntity<IssueRoleResponse> updateIssueRole(@PathVariable Long id, @Valid @RequestBody IssueRoleRequest request) {
        return ResponseEntity.ok(issueRoleService.updateIssueRole(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete an issue role", description = "Deletes an issue role. Requires ADMIN role.")
    public ResponseEntity<Void> deleteIssueRole(@PathVariable Long id) {
        issueRoleService.deleteIssueRole(id);
        return ResponseEntity.noContent().build();
    }
}