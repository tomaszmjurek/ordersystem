package com.tj.orderservice.api;


import com.tj.orderservice.dto.CreateOrderRequestDTO;
import com.tj.orderservice.dto.OrderResponseDTO;
import com.tj.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public Mono<OrderResponseDTO> createOrder(@RequestBody CreateOrderRequestDTO orderRequestDTO) {
        return orderService.createOrderImperative(orderRequestDTO);
    }

    @GetMapping
    public Flux<OrderResponseDTO> getOrders() {
        return orderService.getAllOrders();
    }

}
