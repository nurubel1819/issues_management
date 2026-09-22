package com.example.issues_management.common.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
public abstract class BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(updatable = false)
    private Long createdById;

    private Long updatedById;

    @PrePersist
    void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;

        Long currentUserId = getCurrentUserId();
        createdById = currentUserId;
        updatedById = currentUserId;
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = LocalDateTime.now();
        updatedById = getCurrentUserId();
    }

    private Long getCurrentUserId() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()
                && authentication.getPrincipal() instanceof UserDetails) {
                Object principal = authentication.getPrincipal();

                // If principal has getId() method (User entity implements UserDetails)
                if (principal instanceof com.example.issues_management.auth.entity.User) {
                    return ((com.example.issues_management.auth.entity.User) principal).getId();
                }
            }
        } catch (Exception e) {
            // If we can't get the user ID, return null
            return null;
        }
        return null;
    }
}

