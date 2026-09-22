package com.example.issues_management.domain.sprints.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.LocalDate;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class SprintRequest {
    @NotBlank
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean active;
}
