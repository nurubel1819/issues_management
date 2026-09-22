package com.example.issues_management.domain.teams.entitys;

import com.example.issues_management.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "team")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Team extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(length = 1000)
    private String description;
}
