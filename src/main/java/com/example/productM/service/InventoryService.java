package com.example.productM.service;

import com.example.productM.dto.InventoryRequest;
import com.example.productM.entity.Inventory;
import com.example.productM.entity.Product;
import com.example.productM.repository.InventoryRepository;
import com.example.productM.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ProductRepository productRepository;


    public Inventory addInventory(InventoryRequest inventoryRequest) {

        Product product = productRepository.findById(inventoryRequest.getProductId()).orElse(null);

        if (product == null) {
            throw new RuntimeException("Product not found");
        }

        Inventory inventory = inventoryRepository.findByProductId(product.getId()).orElse(null);

        if (inventory == null) {
            inventory = new Inventory();
            inventory.setProduct(product);
            inventory.setQuantity(inventoryRequest.getQuantity());
        } else {
            inventory.setQuantity(inventoryRequest.getQuantity());
        }

        return inventoryRepository.save(inventory);
    }


    public Inventory getInventoryByProductId(Long productId) {

        Inventory inventory = inventoryRepository.findByProductId(productId).orElse(null);

        if (inventory == null) {
            throw new RuntimeException("Inventory not found");
        }

        return inventory;
    }
}