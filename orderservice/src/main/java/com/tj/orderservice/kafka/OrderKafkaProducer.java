package com.tj.orderservice.kafka;

import com.tj.orderservice.model.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderKafkaProducer {

    private final KafkaTemplate<String, Order> kafkaTemplate;

    @Value("${spring.kafka.topic}")
    private String topicName;

    public Flux<Void> sendMessage(Order order){
        kafkaTemplate.send(topicName, order);
        log.info("Order {} sent to topic {}", order.orderId(), topicName);
        return Flux.empty();
    }
}
