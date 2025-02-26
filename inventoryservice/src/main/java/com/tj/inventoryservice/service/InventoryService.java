package com.tj.inventoryservice.service;

import com.tj.inventoryservice.dto.CreateProductRequestDTO;
import com.tj.inventoryservice.dto.UpdateProductRequestDTO;
import com.tj.inventoryservice.exception.ProductAlreadyExistsException;
import com.tj.inventoryservice.exception.ProductDoesNotExistException;
import com.tj.inventoryservice.model.Item;
import com.tj.inventoryservice.model.Order;
import com.tj.inventoryservice.model.Product;
import com.tj.inventoryservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final ProductRepository productRepository;

    public void processOrder(Order order) {
        final var itemsToSend = takeItemsFromInventory(order.items());
        if (itemsToSend.size() > 0) {
            log.info("Will be shipping items: {}", itemsToSend);
        } else {
            log.warn("Cannot finalize order, no products available");
        }
        // todo kafka send to shipping service
    }

    /**
     * @param itemsToTake - list of items to take from inventory
     * @return items taken from inventory based on their availability. Order items quantity can be reduced
     */
    public List<Item> takeItemsFromInventory(List<Item> itemsToTake) { // fixme jpa transaction issue
        List<Item> takenItems = new ArrayList<>();

        itemsToTake.forEach(item -> {
            try {
                Product product = productRepository.findById(item.productId()).orElseThrow();
                int availableQuantity = getAvailableQuantity(product, item);
                if (availableQuantity > 0) {
                    log.info("Taking {} product(s) {} from {} available", availableQuantity, product.getName(), product.getQuantity());
                    takenItems.add(new Item(item.productId(), availableQuantity));
                    product.setQuantity(product.getQuantity() - availableQuantity);
                    productRepository.save(product);
                }
            } catch (NoSuchElementException e) {
                log.error("Product with id {} was not found and will be skipped", item.productId());
            } catch (RuntimeException e) {
                log.error("Could not process order", e);
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

    public UUID addNewProduct(CreateProductRequestDTO requestDTO) {
        Optional<Product> existing = productRepository.findByName(requestDTO.name());
        if (existing.isPresent()) {
            log.warn("Cannot create product {} that already exists", requestDTO.name());
            throw new ProductAlreadyExistsException(requestDTO.name());
        }
        Product product = new Product(UUID.randomUUID(), requestDTO.name(), requestDTO.quantity());
        productRepository.save(product);
        log.info("New product {} added", product.getId());
        return product.getId();
    }

    public void updateProduct(String productId, UpdateProductRequestDTO requestDTO) {
        var product = productRepository.findById(UUID.fromString(productId));
        if (product.isEmpty()) throw new ProductDoesNotExistException(productId);
        if (requestDTO.name() != null) product.get().setName(requestDTO.name());
        if (requestDTO.quantity() != null) product.get().setQuantity(requestDTO.quantity());
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }
}
