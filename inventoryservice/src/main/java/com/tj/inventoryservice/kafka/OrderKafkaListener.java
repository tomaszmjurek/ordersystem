package com.tj.inventoryservice.kafka;

import com.tj.inventoryservice.model.Order;
import com.tj.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderKafkaListener {

    private final InventoryService inventoryService;

    @KafkaListener(topics = "order-requests")
    public void listen(Order order) {
        log.info("Received order: {}", order);
        inventoryService.processOrder(order);
    }
}
