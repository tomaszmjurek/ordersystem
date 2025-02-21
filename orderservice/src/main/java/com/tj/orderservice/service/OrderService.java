package com.tj.orderservice.service;

import com.tj.orderservice.dto.CreateOrderRequestDTO;
import com.tj.orderservice.dto.OrderResponseDTO;
import com.tj.orderservice.kafka.OrderKafkaProducer;
import com.tj.orderservice.mapper.OrderMapper;
import com.tj.orderservice.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderMapper orderMapper;
    private final OrderKafkaProducer kafkaProducer;
    List<Order> allOrders = new ArrayList<>();

    public Mono<OrderResponseDTO> createOrderImperative(CreateOrderRequestDTO orderRequestDTO) {
        return Mono.fromCallable(() -> orderMapper.map(orderRequestDTO)).log()
                .flatMap(order ->
                        Mono.when(
                                Mono.fromRunnable(() -> allOrders.add(order)),
                                kafkaProducer.sendMessage(order) // fixme this should not block API response
                        ).thenReturn(order)
                )
                .map(orderMapper::map);
    }

    public Flux<OrderResponseDTO> getAllOrders() {
        return Flux.fromIterable(allOrders.stream().map(orderMapper::map).toList()).log();
    }
}
