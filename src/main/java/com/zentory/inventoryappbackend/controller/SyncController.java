// SyncController.java
package com.zentory.inventoryappbackend.controller;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/sync")
@CrossOrigin(origins = "*")
public class SyncController {
    
    private static final Logger logger = LoggerFactory.getLogger(SyncController.class);
    private final Gson gson = new Gson(); 
    
    @GetMapping("/health")
    public Map<String, Object> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "online");
        response.put("message", "Zentory Cloud API is operational");
        response.put("timestamp", System.currentTimeMillis());
        response.put("version", "1.0.0");
        logger.info("Health check performed");
        return response;
    }
    
    @PostMapping("/batch")
    public Map<String, Object> receiveBatch(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        List<Integer> syncedIds = new ArrayList<>();
        
        try {
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> operations = (List<Map<String, Object>>) request.get("operations");
            String deviceId = (String) request.get("device_id");
            
            logger.info("Received {} operations from device: {}", operations.size(), deviceId);
            
            for (Map<String, Object> op : operations) {
                String tableName = (String) op.get("tableName");
                String operationType = (String) op.get("operationType");
                String recordId = (String) op.get("recordId");
                int localId = ((Number) op.get("id")).intValue();
                
                boolean success = processOperation(tableName, operationType, recordId);
                
                if (success) {
                    syncedIds.add(localId);
                    logger.info("Synced {} on {} ID: {}", operationType, tableName, recordId);
                }
            }
            
            response.put("syncedIds", syncedIds);
            response.put("newSyncToken", String.valueOf(System.currentTimeMillis()));
            response.put("success", true);
            
        } catch (Exception e) {
            logger.error("Batch sync failed", e);
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        
        return response;
    }
    
    @GetMapping("/changes")
    public Map<String, Object> getChanges(
            @RequestParam String since, 
            @RequestParam String device) {
        
        Map<String, Object> response = new HashMap<>();
        List<Map<String, Object>> products = new ArrayList<>();
        
        try {
            logger.info("Device {} requesting changes since {}", device, since);
            response.put("products", products);
            response.put("deletedProductIds", new ArrayList<>());
            response.put("newSyncToken", String.valueOf(System.currentTimeMillis()));
            response.put("success", true);
            
        } catch (Exception e) {
            logger.error("Failed to get changes", e);
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        
        return response;
    }
    
    private boolean processOperation(String tableName, String operationType, String recordId) {
        logger.info("Processing: {} on {} ID: {}", operationType, tableName, recordId);
        return true;
    }
}