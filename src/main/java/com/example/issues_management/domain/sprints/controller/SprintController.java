package com.example.issues_management.domain.sprints.controller;

import com.example.issues_management.domain.issues.enums.EstimationStatus;
import com.example.issues_management.domain.issues.enums.IssueStatus;
import com.example.issues_management.domain.sprints.dtos.SprintReportResponse;
import com.example.issues_management.domain.sprints.dtos.SprintRequest;
import com.example.issues_management.domain.sprints.dtos.SprintResponse;
import com.example.issues_management.domain.sprints.service.SprintReportService;
import com.example.issues_management.domain.sprints.service.SprintService;
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
@RequestMapping("/api/sprints")
@RequiredArgsConstructor
@Tag(name = "Sprint Management", description = "APIs for managing sprints")
@SecurityRequirement(name = "bearerAuth")
public class SprintController {

    private final SprintService sprintService;
    private final SprintReportService sprintReportService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new sprint", description = "Creates a new sprint. Requires ADMIN role.")
    public ResponseEntity<SprintResponse> createSprint(@Valid @RequestBody SprintRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sprintService.createSprint(request));
    }

    @GetMapping
    @Operation(summary = "Get all sprints")
    public ResponseEntity<List<SprintResponse>> getAllSprints() {
        return ResponseEntity.ok(sprintService.getAllSprints());
    }

    @GetMapping("/active")
    @Operation(summary = "Get active sprints")
    public ResponseEntity<List<SprintResponse>> getActiveSprints() {
        return ResponseEntity.ok(sprintService.getActiveSprints());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get sprint by ID")
    public ResponseEntity<SprintResponse> getSprintById(@PathVariable Long id) {
        return ResponseEntity.ok(sprintService.getSprintById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update a sprint", description = "Updates an existing sprint. Requires ADMIN role.")
    public ResponseEntity<SprintResponse> updateSprint(@PathVariable Long id, @Valid @RequestBody SprintRequest request) {
        return ResponseEntity.ok(sprintService.updateSprint(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a sprint", description = "Deletes a sprint. Requires ADMIN role.")
    public ResponseEntity<Void> deleteSprint(@PathVariable Long id) {
        sprintService.deleteSprint(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/details/dashboard/{sprintId}")
    @Operation(summary = "Get sprint dashboard details",
            description = "Returns overview, issue-status distribution, estimation summary, and delivery details for a sprint. Optionally filter by issueStatus and/or estimationStatus.")
    public ResponseEntity<SprintReportResponse> getSprintReport(
            @PathVariable Long sprintId,
            @RequestParam(required = false) IssueStatus issueStatus,
            @RequestParam(required = false) EstimationStatus estimationStatus) {
        return ResponseEntity.ok(sprintReportService.getSprintReport(sprintId, issueStatus, estimationStatus));
    }
}
