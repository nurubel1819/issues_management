package com.example.issues_management.domain.sprints.entitys;

import com.example.issues_management.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "sprint")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Sprint extends BaseEntity {

    @Column(nullable = false)
    private String name;

    private LocalDate startDate;
    private LocalDate endDate;

    @Column(nullable = false)
    private boolean active;
}
