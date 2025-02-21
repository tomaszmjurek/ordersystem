# Order Service
Microservice responsible for receiving and processing customer orders. It publishes order events to the Inventory Service.

## Motivation
This service is part of System created to better understand Kafka, Event Driven Architecture, Reactive Programming using Webflux and Spring Boot.


## Running application
1. Make sure you have Docker environment configured and running on your machine (ie. Docker Desktop for Windows)
2. Start Kafka broker and Kafka UI locally using command:

    `docker compose up -d`
3. Run _OrderServiceApplication_
