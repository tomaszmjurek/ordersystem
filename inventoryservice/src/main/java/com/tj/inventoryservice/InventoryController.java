package com.tj.inventoryservice;

import com.tj.inventoryservice.dto.CreateProductRequestDTO;
import com.tj.inventoryservice.dto.UpdateProductRequestDTO;
import com.tj.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController("api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    public HttpStatus post(@RequestBody CreateProductRequestDTO requestDTO) {
        inventoryService.addNewProduct(requestDTO);
        return HttpStatus.CREATED;
    }

    @PatchMapping("/{productId}")
    public HttpStatus patch(@PathVariable String productId, @RequestBody UpdateProductRequestDTO requestDTO) {
        inventoryService.updateProduct(productId, requestDTO);
        return HttpStatus.OK;
    }
}
