package com.zentory.inventoryappbackend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sync_queue")
public class SyncQueue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "table_name", nullable = false)
    private String tableName;

    @Column(name = "operation_type", nullable = false)
    private String operationType;

    @Column(name = "record_id", nullable = false)
    private String recordId;

    @Lob
    @Column(name = "json_data", nullable = false)
    private String jsonData;

    private String status = "PENDING";
    private Integer retryCount = 0;
    private LocalDateTime createdAt;
    private LocalDateTime syncedAt;
    private String errorMessage;

    public SyncQueue() {}
}
