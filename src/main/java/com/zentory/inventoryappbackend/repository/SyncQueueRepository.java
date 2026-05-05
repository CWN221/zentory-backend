package com.zentory.inventoryappbackend.repository;

import com.zentory.inventoryappbackend.model.SyncQueue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SyncQueueRepository extends JpaRepository<SyncQueue, Long> {
}
