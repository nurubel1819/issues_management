package com.example.issues_management.domain.issues.controller;

import com.example.issues_management.domain.issues.dtos.AssignForTeamRequest;
import com.example.issues_management.domain.issues.dtos.AssignForTeamResponse;
import com.example.issues_management.domain.issues.service.AssignForTeamService;
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
@RequestMapping("/api/issue-teams")
@RequiredArgsConstructor
@Tag(name = "Issue Team Assignment", description = "APIs for assigning teams to issues")
@SecurityRequirement(name = "bearerAuth")
public class AssignForTeamController {

    private final AssignForTeamService service;

    @PostMapping
    @Operation(summary = "Assign a team to an issue")
    public ResponseEntity<AssignForTeamResponse> assign(@Valid @RequestBody AssignForTeamRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.assign(request));
    }

    @GetMapping("/issue/{issueId}")
    @Operation(summary = "Get all teams assigned to an issue")
    public ResponseEntity<List<AssignForTeamResponse>> getByIssue(@PathVariable Long issueId) {
        return ResponseEntity.ok(service.getByIssue(issueId));
    }

    @GetMapping("/team/{teamId}")
    @Operation(summary = "Get all issues assigned to a team")
    public ResponseEntity<List<AssignForTeamResponse>> getByTeam(@PathVariable Long teamId) {
        return ResponseEntity.ok(service.getByTeam(teamId));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove a team assignment")
    public ResponseEntity<Void> remove(@PathVariable Long id) {
        service.remove(id);
        return ResponseEntity.noContent().build();
    }
}
