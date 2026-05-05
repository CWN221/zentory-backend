package com.zentory.inventoryappbackend;

import com.zentory.inventoryappbackend.model.Category;
import com.zentory.inventoryappbackend.model.Product;
import com.zentory.inventoryappbackend.repository.CategoryRepository;
import com.zentory.inventoryappbackend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupRunner implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    public StartupRunner(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Datasource in use: " + datasourceUrl);        
    }
}
