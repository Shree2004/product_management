package com.example.productM.controller;

import com.example.productM.dto.InventoryRequest;
import com.example.productM.entity.Inventory;
import com.example.productM.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping
    public Inventory addInventory(@Valid @RequestBody InventoryRequest inventoryRequest) {
        return inventoryService.addInventory(inventoryRequest);
    }

    @GetMapping("/product/{productId}")
    public Inventory getInventoryByProductId(@PathVariable Long productId) {
        return inventoryService.getInventoryByProductId(productId);
    }
}