package com.tj.inventoryservice.api;

import com.tj.inventoryservice.dto.CreateProductRequestDTO;
import com.tj.inventoryservice.dto.UpdateProductRequestDTO;
import com.tj.inventoryservice.exception.ProductAlreadyExistsException;
import com.tj.inventoryservice.model.Product;
import com.tj.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<UUID> post(@RequestBody CreateProductRequestDTO requestDTO) {
        try {
            UUID id = inventoryService.addNewProduct(requestDTO);
            return new ResponseEntity<>(id, HttpStatus.CREATED);
        } catch (ProductAlreadyExistsException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<Void> patch(@PathVariable String productId, @RequestBody UpdateProductRequestDTO requestDTO) {
        inventoryService.updateProduct(productId, requestDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Product>> get() { // todo implement DTO
        var products = inventoryService.getProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
