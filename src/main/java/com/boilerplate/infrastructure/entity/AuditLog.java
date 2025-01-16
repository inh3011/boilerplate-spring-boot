package com.boilerplate.infrastructure.entity;

import com.boilerplate.domain.enumuration.Action;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "audit_logs")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "action")
    private Action action;

    @Column(name = "entity_name")
    private String entityName;

    @Column(name = "entity_id")
    private Long entityId;

    @Column(name = "username")
    private String username;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "request", columnDefinition = "TEXT")
    private String request;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Builder
    public AuditLog(
            Action action,
            String entityName,
            Long entityId,
            String username,
            String request,
            Long userId
    ) {
        this.action = action;
        this.entityName = entityName;
        this.entityId = entityId;
        this.username = username;
        this.userId = userId;
        this.request = request;
        this.createdAt = LocalDateTime.now();
    }
}