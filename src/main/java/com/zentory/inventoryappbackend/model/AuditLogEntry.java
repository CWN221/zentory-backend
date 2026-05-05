package com.zentory.inventoryappbackend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
public class AuditLogEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String role;
    private String action;
    private String entityType;
    private Long entityId;
    @Lob
    private String details;
    private LocalDateTime createdAt;

    public AuditLogEntry() {}
}
