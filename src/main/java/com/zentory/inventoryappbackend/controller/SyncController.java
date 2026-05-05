// SyncController.java
package com.zentory.inventoryappbackend.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.zentory.inventoryappbackend.model.Category;
import com.zentory.inventoryappbackend.model.Product;
import com.zentory.inventoryappbackend.repository.CategoryRepository;
import com.zentory.inventoryappbackend.repository.ProductRepository;
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
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    
    public SyncController(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }
    
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
                String jsonData = op.get("jsonData") != null ? String.valueOf(op.get("jsonData")) : null;
                int localId = ((Number) op.get("id")).intValue();
                
                boolean success = processOperation(tableName, operationType, recordId, jsonData);
                
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
    
    private boolean processOperation(String tableName, String operationType, String recordId, String jsonData) {
        logger.info("Processing: {} on {} ID: {}", operationType, tableName, recordId);

        try {
            if ("products".equalsIgnoreCase(tableName)) {
                String op = operationType == null ? "" : operationType.toUpperCase();
                if ("INSERT".equals(op) || "UPDATE".equals(op)) {
                    if (jsonData == null) {
                        logger.warn("No jsonData provided for product {}", recordId);
                        return false;
                    }
                    JsonObject obj = gson.fromJson(jsonData, JsonObject.class);

                    // Resolve or create category
                    Category category = null;
                    if (obj.has("categoryId") && !obj.get("categoryId").isJsonNull()) {
                        try {
                            long cid = obj.get("categoryId").getAsLong();
                            category = categoryRepository.findById(cid).orElse(null);
                        } catch (Exception ignored) {}
                    }
                    if (category == null && obj.has("categoryName") && !obj.get("categoryName").isJsonNull()) {
                        String cname = obj.get("categoryName").getAsString();
                        category = categoryRepository.findByName(cname);
                        if (category == null) {
                            category = new Category(cname);
                            category = categoryRepository.save(category);
                        }
                    }

                    Product product;
                    try {
                        Long id = Long.parseLong(recordId);
                        product = productRepository.findById(id).orElse(new Product());
                        product.setId(id);
                    } catch (NumberFormatException e) {
                        product = new Product();
                    }

                    if (obj.has("name") && !obj.get("name").isJsonNull()) product.setName(obj.get("name").getAsString());
                    if (category != null) product.setCategory(category);
                    if (obj.has("quantity") && !obj.get("quantity").isJsonNull()) product.setQuantity(obj.get("quantity").getAsInt());
                    if (obj.has("price") && !obj.get("price").isJsonNull()) product.setPrice(obj.get("price").getAsDouble());
                    if (obj.has("is_active") && !obj.get("is_active").isJsonNull()) product.setIsActive(obj.get("is_active").getAsInt() != 0);

                    productRepository.save(product);
                    return true;

                } else if ("DELETE".equals(op)) {
                    try {
                        Long id = Long.parseLong(recordId);
                        Optional<Product> p = productRepository.findById(id);
                        if (p.isPresent()) {
                            Product prod = p.get();
                            prod.setIsActive(false);
                            productRepository.save(prod);
                        }
                        return true;
                    } catch (Exception e) {
                        logger.error("Failed to delete product: {}", recordId, e);
                        return false;
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error processing operation", e);
            return false;
        }

        // Unknown table - ignore but report success to avoid retries
        return true;
    }
}