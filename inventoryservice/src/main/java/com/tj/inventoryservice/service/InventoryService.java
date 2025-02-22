package com.tj.inventoryservice.service;

import com.tj.inventoryservice.dto.CreateProductRequestDTO;
import com.tj.inventoryservice.dto.UpdateProductRequestDTO;
import com.tj.inventoryservice.model.Item;
import com.tj.inventoryservice.model.Order;
import com.tj.inventoryservice.model.Product;
import com.tj.inventoryservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final ProductRepository productRepository;

    public void processOrder(Order order) {
        final var itemsToSend = takeItemsFromInventory(order.items());
        log.info("Will be sending items: {}", itemsToSend);
        // todo kafka send to shipping service
    }

    /**
     * @param itemsToTake - list of items to take from inventory
     * @return items taken from inventory based on their availability. Order items quantity can be reduced
     */
    public List<Item> takeItemsFromInventory(List<Item> itemsToTake) { // fixme jpa transaction issue
        List<Item> takenItems = new ArrayList<>();

        itemsToTake.forEach(item -> {
            Product product = productRepository.findById(item.productId()).orElseThrow(); // todo not present handle
            int availableQuantity = getAvailableQuantity(product, item);
            if (availableQuantity > 0) {
                log.info("Taking {} products {} from stock", availableQuantity, product.getId());
                takenItems.add(new Item(item.productId(), availableQuantity));
                product.setQuantity(product.getQuantity() - availableQuantity);
            }
        });

        return takenItems;
    }

    private int getAvailableQuantity(Product product, Item item) {
        int quantityInStock = product.getQuantity();
        if (quantityInStock < item.quantity()) {
            log.warn("Product is available but lesser than ordered");
            return quantityInStock;
        }
        return item.quantity();
    }

    public void addNewProduct(CreateProductRequestDTO requestDTO) {
        Product product = new Product(requestDTO.id(), requestDTO.name(), requestDTO.quantity());
        productRepository.save(product);
    }

    public void updateProduct(String productId, UpdateProductRequestDTO requestDTO) {
        Product product = productRepository.findById(productId).orElseThrow();
        if (requestDTO.name() != null) product.setName(requestDTO.name());
        if (requestDTO.quantity() != null) product.setQuantity(requestDTO.quantity());
    }
}
