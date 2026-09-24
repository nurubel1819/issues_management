package com.example.issues_management.domain.issues.entitys;

import com.example.issues_management.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "issue_role")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IssueRole extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name; // "Agent App", "User App", "Doctor", "STM"

    @Column(length = 500)
    private String description;
}