# Order System

System for processing shopping orders. Writen in Event Driven Architecture. Enriched with REST controllers for accessing and managing the orders.

<img src=https://github.com/tomaszmjurek/readme-images/blob/master/order-system.png>

## Motivation
This service is created for learning with fun to better understand Kafka, Event Driven Architecture, Reactive Programming using Webflux and Spring Boot.

## Microservices:

- Order Service: Manages customer orders, including order creation.
- Inventory Service: Keeps track of product inventory and updates stock levels.
- Shipping Service: Handles the shipping process and updates shipping status. (to be added...)

## Workflow:

1. The Order Service receives an order request via API and publishes an event to a Kafka topic "order-requests".
2. The Inventory Service listens to the Kafka topic "order-requests", updates the inventory, and publishes an event on topic "shipping-requests".
3. The Shipping Service listens to the "shipping-requests" topic, processes the shipping, and publishes an event with the "order-statuses" during the whole process of shipping the order.
4. Order Services listens to events with "order-statuses". Exposes order status to API clients.

## Running application
1. Make sure you have Docker environment configured and running on your machine (ie. Docker Desktop for Windows)
2. Start all required containers:
    `docker compose up -d`
3. Run _OrderServiceApplication_
4. Run _InventoryServiceApplication_
