package com.zentory.inventoryappbackend.repository;

import com.zentory.inventoryappbackend.model.AuditLogEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLogEntry, Long> {
}
