package com.boilerplate.infrastructure.entity;

import com.boilerplate.domain.enumuration.Action;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name = "audit_logs")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Action action;
    private String entityName;
    private Long entityId;
    private String username;
    private LocalDateTime timestamp;

    public AuditLog(Action action, String entityName, Long entityId, String username) {
        this.action = action;
        this.entityName = entityName;
        this.entityId = entityId;
        this.username = username;
        this.timestamp = LocalDateTime.now();
    }
}