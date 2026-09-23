package com.example.issues_management.domain.issues.controller;

import com.example.issues_management.domain.issues.dtos.AssignForEstimationRequest;
import com.example.issues_management.domain.issues.dtos.AssignForEstimationResponse;
import com.example.issues_management.domain.issues.dtos.UserEstimationDetailsResponse;
import com.example.issues_management.domain.issues.enums.EstimationStatus;
import com.example.issues_management.domain.issues.service.AssignForEstimationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issue-assignments")
@RequiredArgsConstructor
@Tag(name = "Issue Estimation Assignment", description = "APIs for assigning users to issues with estimation")
@SecurityRequirement(name = "bearerAuth")
public class AssignForEstimationController {

    private final AssignForEstimationService service;

    @PostMapping
    @Operation(summary = "Assign a user to an issue with estimation")
    public ResponseEntity<AssignForEstimationResponse> assign(@Valid @RequestBody AssignForEstimationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.assign(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing estimation assignment")
    public ResponseEntity<AssignForEstimationResponse> update(
            @PathVariable Long id, @Valid @RequestBody AssignForEstimationRequest request) {
        return ResponseEntity.ok(service.updateEstimation(id, request));
    }

    @GetMapping("/issue/{issueId}")
    @Operation(summary = "Get all user assignments for an issue")
    public ResponseEntity<List<AssignForEstimationResponse>> getByIssue(@PathVariable Long issueId) {
        return ResponseEntity.ok(service.getByIssue(issueId));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get all issue assignments for a user",
            description = "Returns issue assignments for a user. Optionally filter by estimationStatus.")
    public ResponseEntity<List<AssignForEstimationResponse>> getByUser(
            @PathVariable Long userId,
            @RequestParam(required = false) EstimationStatus status) {
        return ResponseEntity.ok(service.getByUser(userId, status));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove an estimation assignment")
    public ResponseEntity<Void> remove(@PathVariable Long id) {
        service.remove(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/details/{userId}")
    @Operation(summary = "Get user's estimation details",
            description = "Returns status-wise count, status-wise total estimated time, and last delivery date for a user. Optionally filter by estimationStatus.")
    public ResponseEntity<UserEstimationDetailsResponse> getUserEstimationDetails(
            @PathVariable Long userId,
            @RequestParam(required = false) EstimationStatus status) {
        return ResponseEntity.ok(service.getUserEstimationDetails(userId, status));
    }
}
